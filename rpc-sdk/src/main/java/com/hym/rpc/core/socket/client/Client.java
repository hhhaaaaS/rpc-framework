/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.socket.client
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  18:53
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.socket.client;

import com.alibaba.fastjson.JSON;
import com.hym.rpc.config.ClientConfig;
import com.hym.rpc.constant.Constants;
import com.hym.rpc.core.encoder.RpcDecoder;
import com.hym.rpc.core.encoder.RpcEncoder;
import com.hym.rpc.core.protocol.RpcInvocation;
import com.hym.rpc.core.protocol.RpcProtocol;
import com.hym.rpc.core.proxy.RpcReference;
import com.hym.rpc.core.proxy.jdk.JDKProxyFactory;
import com.hym.rpc.core.socket.client.handle.ClientHandler;
import com.hym.rpc.rpcInterface.DataService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.hym.rpc.common.cache.CommonClientCache.SEND_QUEUE;

@Data
@Slf4j
public class Client {


    public static EventLoopGroup clientGroup = new NioEventLoopGroup();

    private ClientConfig clientConfig;



    public static void main(String[] args) throws Throwable {
        Client client = new Client();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setPort(9090);
        clientConfig.setServerAddr("localhost");
        client.setClientConfig(clientConfig);
        RpcReference rpcReference = client.startClientApplication();
        DataService dataService = rpcReference.get(DataService.class);
        for(int i=0;i<100;i++){
            log.info("循环发送:{}",i);
            String result = dataService.sendData("test");
            System.out.println(result);
        }
    }


    public RpcReference startClientApplication() throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //管道中初始化一些逻辑，这里包含了上边所说的编解码器和客户端响应类
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        //常规的链接netty服务端
        ChannelFuture channelFuture = bootstrap.connect(clientConfig.getServerAddr(), clientConfig.getPort()).sync();
        log.info("============ 服务启动 ============");
        this.startClient(channelFuture);
        //这里注入了一个代理工厂，这个代理类在下文会仔细介绍
        RpcReference rpcReference = new RpcReference(new JDKProxyFactory());
        return rpcReference;
    }

    /**
     * 开启发送线程，专门从事将数据包发送给服务端，起到一个解耦的效果
     * @param channelFuture
     */
    private void startClient(ChannelFuture channelFuture) {
        Thread asyncSendJob = new Thread(new AsyncSendJob(channelFuture));
        asyncSendJob.start();
    }

    /**
     * 异步发送信息任务
     *
     */
    class AsyncSendJob implements Runnable {

        private ChannelFuture channelFuture;

        public AsyncSendJob(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        @Override
        public void run() {
            int count=1;
            while (true) {
                try {
                    log.info("client循环发送次数：{}",count);
                    //阻塞模式
                    RpcInvocation data = SEND_QUEUE.take();
                    //将RpcInvocation封装到RpcProtocol对象中，然后发送给服务端，这里正好对应了上文中的ServerHandler
                    String json = JSON.toJSONString(data);
                    log.info("client发送数据:{}",json);
                    byte[] bytes = json.getBytes();
                    RpcProtocol rpcProtocol = new RpcProtocol(bytes);
                    rpcProtocol.setMagicNumber(Constants.MAGIC_NUMBER);
                    rpcProtocol.setContentLength(bytes.length);
                    //netty的通道负责发送数据给服务端
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
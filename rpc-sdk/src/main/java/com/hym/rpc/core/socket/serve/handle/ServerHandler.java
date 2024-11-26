/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.socket.serve.handle
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  15:23
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.socket.serve.handle;

import com.alibaba.fastjson.JSON;
import com.hym.rpc.constant.Constants;
import com.hym.rpc.core.protocol.RpcInvocation;
import com.hym.rpc.core.protocol.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

import static com.hym.rpc.common.cache.CommonServerCache.PROVIDER_CLASS_MAP;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("server read");
        //服务端接收数据的时候统一以RpcProtocol协议的格式接收，具体的发送逻辑见方客户端发送部分
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        String json = new String(rpcProtocol.getContent(), 0, rpcProtocol.getContentLength());
        RpcInvocation rpcInvocation = JSON.parseObject(json, RpcInvocation.class);
        //这里的PROVIDER_CLASS_MAP就是一开始预先在启动时候存储的Bean集合
        Object aimObject = PROVIDER_CLASS_MAP.get(rpcInvocation.getTargetServiceName());
        Method[] methods = aimObject.getClass().getDeclaredMethods();
        Object result = null;
        for (Method method : methods) {
            if (method.getName().equals(rpcInvocation.getTargetMethod())) {
                // 通过反射找到目标对象，然后执行目标方法并返回对应值
                if (method.getReturnType().equals(Void.TYPE)) {
                    method.invoke(aimObject, rpcInvocation.getArgs());
                } else {
                    result = method.invoke(aimObject, rpcInvocation.getArgs());
                }
                break;
            }
        }

        rpcInvocation.setResponse(result);
        byte[] bytes = JSON.toJSONString(rpcInvocation).getBytes();
        RpcProtocol respRpcProtocol = new RpcProtocol(bytes);
        //ByteBuf buf = Unpooled.buffer(bytes.length);
        respRpcProtocol.setContentLength(bytes.length);
        respRpcProtocol.setContent(bytes);
        respRpcProtocol.setMagicNumber(Constants.MAGIC_NUMBER);
        log.info("server send,length:{}",bytes.length);
        ctx.writeAndFlush(respRpcProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
    }

}
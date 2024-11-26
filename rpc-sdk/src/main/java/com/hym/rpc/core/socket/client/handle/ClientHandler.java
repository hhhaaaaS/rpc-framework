/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.socket.client.handle
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  18:55
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.socket.client.handle;

import com.alibaba.fastjson.JSON;
import com.hym.rpc.core.protocol.RpcInvocation;
import com.hym.rpc.core.protocol.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import static com.hym.rpc.common.cache.CommonClientCache.RESP_MAP;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        byte[] reqContent = rpcProtocol.getContent();
        String json = new String(reqContent, 0, reqContent.length);
        RpcInvocation rpcInvocation = JSON.parseObject(json, RpcInvocation.class);
        if (!RESP_MAP.containsKey(rpcInvocation.getUuid())) {
            throw new IllegalArgumentException("server response is error!");
        }
        log.info("client receive:{}", json);
        RESP_MAP.put(rpcInvocation.getUuid(), rpcInvocation);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("client 断开连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(cause+"");
        super.exceptionCaught(ctx, cause);
    }
}
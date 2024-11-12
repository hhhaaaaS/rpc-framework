/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.socket.client.handle
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  18:55
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.socket.client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.encoder
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  15:14
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.encoder;

import com.hym.rpc.core.protocol.RpcProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol>{


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol msg, ByteBuf out) throws Exception {
        out.writeShort(msg.getMagicNumber());
        out.writeInt(msg.getContentLength());
        out.writeBytes(msg.getContent());
        log.info("encode: "+msg);
    }

   /* @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf out) throws Exception {
        out.writeShort(msg.getMagicNumber());
        out.writeInt(msg.getContentLength());
        out.writeBytes(msg.getContent());
        log.info("encode: "+msg);
    }*/
}
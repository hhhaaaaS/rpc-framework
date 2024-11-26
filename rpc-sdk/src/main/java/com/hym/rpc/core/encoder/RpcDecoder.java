/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.encoder
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  15:16
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.encoder;

import com.hym.rpc.constant.Constants;
import com.hym.rpc.core.protocol.RpcProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    /**
     * 协议的开头部分的标准长度
     */
    public final int BASE_LENGTH = 2 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        log.info("decode :{}",byteBuf);
        if (byteBuf.readableBytes() >= BASE_LENGTH) {
            //防止收到一些体积过大的数据包
            if (byteBuf.readableBytes() > 1000) {
                byteBuf.skipBytes(byteBuf.readableBytes());
            }
            int beginReader;
            while (true) {
                beginReader = byteBuf.readerIndex();
                byteBuf.markReaderIndex();
                //这里对应了RpcProtocol的魔数
                if (byteBuf.readShort() == Constants.MAGIC_NUMBER) {
                    break;
                } else {
                    // 不是魔数开头，说明是非法的客户端发来的数据包
                    ctx.close();
                    return;
                }
            }
            //这里对应了RpcProtocol对象的contentLength字段
            int length = byteBuf.readInt();
            //说明剩余的数据包不是完整的，这里需要重置下读索引
            if (byteBuf.readableBytes() < length) {
                byteBuf.readerIndex(beginReader);
                return;
            }
            //这里其实就是实际的RpcProtocol对象的content字段
            byte[] data = new byte[length];
            byteBuf.readBytes(data);
            RpcProtocol rpcProtocol = new RpcProtocol(data);
            rpcProtocol.setContentLength(length);
            out.add(rpcProtocol);
        }
    }
}
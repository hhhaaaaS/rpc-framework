/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.protocol
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  15:12
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcProtocol implements Serializable {

    public RpcProtocol(byte[] content) {
        this.content = content;
    }

    private static final long serialVersionUID = 5359096060555795690L;

    private short magicNumber = 0;
    private int contentLength;
    //这个字段其实是RpcInvocation类的字节数组，在RpcInvocation中包含了更多的调用信息，详情见下方介绍
    private byte[] content;

}
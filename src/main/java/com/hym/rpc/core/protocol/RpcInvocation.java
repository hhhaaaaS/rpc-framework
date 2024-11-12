/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.protocol
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  15:13
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.protocol;

import lombok.Data;

@Data
public class RpcInvocation {

    //请求的目标方法，例如findUser
    private String targetMethod;
    //请求的目标服务名称，例如：com.sise.user.UserService
    private String targetServiceName;
    //请求参数信息
    private Object[] args;
    private String uuid;
    //接口响应的数据塞入这个字段中（如果是异步调用或者void类型，这里就为空）
    private Object response;

}
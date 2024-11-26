/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.proxy
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  19:12
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.proxy;

public interface ProxyFactory {

    <T> T getProxy(final Class clazz) throws Throwable;

}
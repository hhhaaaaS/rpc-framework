/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.proxy
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  19:09
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.proxy;

public class RpcReference {

    public ProxyFactory proxyFactory;

    public RpcReference(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    /**
     * 根据接口类型获取代理对象
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> tClass) throws Throwable {
        return proxyFactory.getProxy(tClass);
    }
}
/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.core.proxy
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  19:13
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.core.proxy.jdk;

import com.hym.rpc.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

public class JDKProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(final Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new JDKClientInvocationHandler(clazz));
    }

}
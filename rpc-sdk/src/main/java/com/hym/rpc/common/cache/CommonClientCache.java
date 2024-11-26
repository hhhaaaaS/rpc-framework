/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.common.cache
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  19:04
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.common.cache;

import com.hym.rpc.core.protocol.RpcInvocation;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class CommonClientCache {

    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue(100);
    public static Map<String,Object> RESP_MAP = new ConcurrentHashMap<>();

}
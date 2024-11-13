/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.rpcInterface.impl
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-13  15:00
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.rpcInterface.impl;

import com.hym.rpc.rpcInterface.DataService;

import java.util.ArrayList;
import java.util.List;

public class DataServiceImpl implements DataService {

    @Override
    public String sendData(String body) {
        System.out.println("己收到的参数长度："+body.length());
        return "success";
    }

    @Override
    public List<String> getList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("idea1");
        arrayList.add("idea2");
        arrayList.add("idea3");
        return arrayList;
    }

    @Override
    public void testError() {

    }

    @Override
    public String testErrorV2() {
        return null;
    }
}
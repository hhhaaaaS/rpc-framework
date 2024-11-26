/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  14:41
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MyApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyApplication.class,args);
         log.info("启动完成");
    }
}
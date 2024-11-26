/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.registery
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-26  11:53
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.registery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegisteryCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisteryCoreApplication.class,args);
    }
}
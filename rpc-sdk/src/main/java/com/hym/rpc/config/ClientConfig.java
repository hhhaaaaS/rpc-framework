/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.rpc.config
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-12  18:53
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.rpc.config;

import lombok.Data;

@Data
public class ClientConfig {

    private String serverAddr;

    private Integer port;
}
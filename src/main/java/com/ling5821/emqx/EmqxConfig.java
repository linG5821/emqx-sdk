package com.ling5821.emqx;

import lombok.Data;

/**
 * @author lsj
 * @date 2021/1/29 20:07
 */
@Data
public class EmqxConfig {
    private String address;
    private String username;
    private String password;
}

package com.ling5821.emqx.model;

import lombok.Getter;

/**
 * @author lsj
 * @date 2021/2/2 14:16
 * 黑名单对象类型
 */
@Getter
public enum BannedType {
    /**
     * 客户端ID
     */
    CLIENT_ID("clientid"),
    /**
     * 用户名
     */
    USERNAME("username"),
    /**
     * 主机
     */
    PEER_HOST("peerhost")
    ;
    private String value;

    BannedType(String value) {
        this.value = value;
    }
}

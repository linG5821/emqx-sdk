package com.ling5821.emqx.model;

import lombok.Getter;

import java.util.StringJoiner;

/**
 * @author lsj
 * @date 2021/2/1 10:32
 */
@Getter
public enum ConnState {

    /**
     * 连接总
     */
    CONNECTED("connected"),
    IDLE("idle"),
    /**
     * 已断开
     */
    DISCONNECTED("disconnected")
    ;
    private String value;

    ConnState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

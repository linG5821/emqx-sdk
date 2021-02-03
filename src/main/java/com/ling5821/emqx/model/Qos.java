package com.ling5821.emqx.model;

import lombok.Getter;

import java.util.StringJoiner;

/**
 * @author lsj
 * @date 2021/2/1 15:30
 */
@Getter
public enum Qos {

    /**
     * QOS 等级0 以此类推
     */
    QOS0(0),
    QOS1(1),
    QOS2(2)
    ;
    private int value;

    Qos(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

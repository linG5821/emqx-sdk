package com.ling5821.emqx.model;

import lombok.Getter;

/**
 * @author lsj
 * @date 2021/2/2 9:28
 */
@Getter
public enum EncodingType {
    /**
     * plain
     */
    PLAIN("plain"),

    /**
     * base64
     */
    BASE64("base64")
    ;

    private String value;

    EncodingType(String value) {
        this.value = value;
    }
}

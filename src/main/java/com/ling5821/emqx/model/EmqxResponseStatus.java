package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author lsj
 * @date 2021/1/30 1:45
 */
@Getter
public enum EmqxResponseStatus {
    /**
     * 成功
     */
    @SerializedName("0")
    SUCCESS(0),

    @SerializedName("101")
    RPC_ERROR(101),

    @SerializedName("101")
    UNKNOWN_ERROR(102),

    @SerializedName("103")
    INVALID_USERNAME_OR_PASSWORD(103),

    @SerializedName("104")
    EMPTY_USERNAME_OR_PASSWORD(104),

    @SerializedName("105")
    USER_NOT_EXISTS(105),

    @SerializedName("106")
    DELETE_ADMIN(106),

    @SerializedName("107")
    PARAMETER_MISSING(107),

    @SerializedName("108")
    INVALID_PARAMETER(108),

    @SerializedName("109")
    ILLEGAL_JSON_PARAMETER(109),

    @SerializedName("110")
    PLUGIN_ENABLE(110),

    @SerializedName("111")
    PLUGIN_DISABLE(111),

    @SerializedName("112")
    CLIENT_OFFLINE(112),

    @SerializedName("113")
    USER_EXISTS(113),

    @SerializedName("114")
    OLD_PASSWORD_ERROR(114),

    @SerializedName("115")
    ILLEGAL_TOPIC(115)
    ;
    private int value;

    EmqxResponseStatus(int value) {
        this.value = value;
    }

}

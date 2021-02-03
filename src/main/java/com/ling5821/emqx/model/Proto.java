package com.ling5821.emqx.model;

import lombok.Getter;

import java.util.StringJoiner;

/**
 * @author lsj
 * @date 2021/2/1 10:53
 */
@Getter
public enum Proto {
    MQTT("MQTT"),
    COAP("CoAP"),
    LWM2M("LwM2M"),
    MQTT_SN("MQTT-SN")
    ;
    private String value;

    Proto(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

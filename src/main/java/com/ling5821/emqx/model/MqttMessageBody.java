package com.ling5821.emqx.model;

import lombok.Data;

/**
 * @author lsj
 * @date 2021/2/1 20:41
 */
@Data
public class MqttMessageBody {
    private String topic;
    private String topics;
    private String clientid;
    private String payload;
    private String encoding;
    private int qos;
    private boolean retain;
}

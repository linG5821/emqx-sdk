package com.ling5821.emqx.model;

import lombok.Data;

/**
 * @author lsj
 * @date 2021/2/2 9:24
 */
@Data
public class SubscribeBody {
    private String topic;
    private String topics;
    private String clientid;
    private int qos;
}

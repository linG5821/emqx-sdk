package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/1 15:07
 */
@NoArgsConstructor
@Data
public class Subscription {

    /**
     * topic : hello/#
     * qos : 0
     * node : emqx@node1.emqx.iot
     * clientid : c40724e52eaf4b2593e139ac4c2fca52
     */

    private String topic;
    private Integer qos;
    private String node;
    private String clientid;
}

package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lsj
 * @date 2021/2/1 15:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SubscriptionQuery extends PageQuery {
    private String clientid;
    private String topic;
    private Qos qos;
    private String share;
    private String _match_topic;
}

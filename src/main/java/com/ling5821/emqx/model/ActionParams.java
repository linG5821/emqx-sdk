package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/3 19:41
 */
@Data
@NoArgsConstructor
public class ActionParams {
    /**
     * target_topic : repub/to/${clientid}
     * target_qos : 1
     * payload_tmpl : ${payload}
     */

    @SerializedName("target_topic")
    private String targetTopic;
    @SerializedName("target_qos")
    private Integer targetQos;
    @SerializedName("payload_tmpl")
    private String payloadTmpl;
    @SerializedName("$resource")
    private String resourceId;
    @SerializedName("forward_topic")
    private String forwardTopic;
}

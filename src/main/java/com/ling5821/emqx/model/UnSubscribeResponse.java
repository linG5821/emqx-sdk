package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 10:01
 */
@NoArgsConstructor
@Data
public class UnSubscribeResponse {

    /**
     * topic : hello/33330
     * code : 0
     * clientid : mqttx_a241e334
     */

    private String topic;
    private Integer code;
    private String clientid;
}

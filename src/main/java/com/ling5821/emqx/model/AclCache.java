package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/1 14:45
 */
@NoArgsConstructor
@Data
public class AclCache {

    /**
     * updated_time : 1612162820894
     * topic : #
     * result : deny
     * access : subscribe
     */

    private Long updatedTime;
    private String topic;
    private String result;
    private String access;
}

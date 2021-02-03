package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/1 16:19
 */
@NoArgsConstructor
@Data
public class Route {

    /**
     * topic : hello/111
     * node : emqx@node2.emqx.iot
     */

    private String topic;
    private String node;
}

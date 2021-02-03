package com.ling5821.emqx.model;

import lombok.Data;

/**
 * @author lsj
 * @date 2021/2/2 9:24
 */
@Data
public class UnSubscribeBody {
    private String topic;
    private String clientid;
}

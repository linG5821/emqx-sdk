package com.ling5821.emqx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 12:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenTopicMetricBody {
    private String topic;
}

package com.ling5821.emqx.model;

import lombok.Data;

import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 11:50
 */
@Data
public class TopicMetrics {
    private String topic;
    private Map<String, Object> metrics;
}

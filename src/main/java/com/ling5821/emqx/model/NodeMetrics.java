package com.ling5821.emqx.model;

import lombok.Data;

import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 11:32
 */
@Data
public class NodeMetrics {
    private String node;
    private Map<String, Object> metrics;
}

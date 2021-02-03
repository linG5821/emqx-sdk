package com.ling5821.emqx.model;

import lombok.Data;

import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 12:15
 */
@Data
public class NodeStats {
    private String node;
    private Map<String, Object> stats;
}

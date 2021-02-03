package com.ling5821.emqx.model;

import lombok.Data;

import java.util.List;

/**
 * @author lsj
 * @date 2021/2/2 10:11
 */
@Data
public class NodePlugin {
    private String node;
    private List<Plugin> plugins;
}

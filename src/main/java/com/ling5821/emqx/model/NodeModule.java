package com.ling5821.emqx.model;

import lombok.Data;

import java.util.List;

/**
 * @author lsj
 * @date 2021/2/2 10:41
 */
@Data
public class NodeModule {
    private String node;
    private List<Module> modules;
}

package com.ling5821.emqx.model;

import lombok.Data;

import java.util.List;

/**
 * @author lsj
 * @date 2021/2/2 10:29
 */
@Data
public class NodeListener {
    private String node;
    private List<Listener> listeners;
}

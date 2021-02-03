package com.ling5821.emqx.model;

import lombok.Data;

import java.util.List;

/**
 * @author lsj
 * @date 2021/2/2 12:20
 */
@Data
public class NodeAlarm {
    private String node;
    private List<Alarm> alarms;
}

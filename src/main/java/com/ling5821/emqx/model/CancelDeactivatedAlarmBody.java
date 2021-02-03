package com.ling5821.emqx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 14:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelDeactivatedAlarmBody {
    private String node;
    private String name;
}

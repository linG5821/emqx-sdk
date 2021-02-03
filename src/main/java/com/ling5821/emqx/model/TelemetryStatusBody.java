package com.ling5821.emqx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 18:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryStatusBody {
    private boolean enabled;
}

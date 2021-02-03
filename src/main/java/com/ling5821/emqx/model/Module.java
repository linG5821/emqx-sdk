package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 10:41
 */
@NoArgsConstructor
@Data
public class Module {

    /**
     * name : emqx_mod_delayed
     * description : EMQ X Delayed Publish Module
     * active : true
     */

    private String name;
    private String description;
    private Boolean active;
}

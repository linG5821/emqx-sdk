package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 14:10
 */
@NoArgsConstructor
@Data
public class Banned {

    /**
     * who : example
     * until : 1582265833
     * reason : undefined
     * by : user
     * at : 1582265533
     * as : clientid
     */

    private String who;
    private Integer until;
    private String reason;
    private String by;
    private Integer at;
    private String as;
}

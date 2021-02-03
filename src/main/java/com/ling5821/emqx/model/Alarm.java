package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 12:20
 */
@NoArgsConstructor
@Data
public class Alarm {

    /**
     * name : high_system_memory_usage
     * message : System memory usage is higher than 70%
     * details : {"high_watermark":70}
     * deactivate_at : 1611924428559986
     * activated : false
     * activate_at : 1611924368742358
     */

    private String name;
    private String message;
    private DetailsBean details;
    private Long deactivateAt;
    private Boolean activated;
    private Long activateAt;

    @NoArgsConstructor
    @Data
    public static class DetailsBean {
        /**
         * high_watermark : 70
         */

        private Integer highWatermark;
    }
}

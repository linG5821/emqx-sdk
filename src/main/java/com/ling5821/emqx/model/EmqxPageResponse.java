package com.ling5821.emqx.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author lsj
 * @date 2021/2/1 11:35
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class EmqxPageResponse<T> extends EmqxResponse<T>{

    private MetaBean meta;

    @NoArgsConstructor
    @Data
    static class MetaBean {
        /**
         * page : 1
         * limit : 10000
         * hasnext : false
         * count : -1
         */

        private Integer page;
        private Integer limit;
        private Boolean hasnext;
        private Integer count;
    }


}

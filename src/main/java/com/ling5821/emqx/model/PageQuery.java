package com.ling5821.emqx.model;

import lombok.Data;

/**
 * @author lsj
 * @date 2021/2/1 12:22
 */
@Data
public class PageQuery {
    private Integer _page = 1;
    private Integer _limit = 10000;
}

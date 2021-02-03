package com.ling5821.emqx.model;

import lombok.Data;


/**
 * @author lsj
 * @date 2021/1/30 1:31
 */
@Data
public class EmqxResponse<T> {
    private EmqxResponseStatus code;
    private T data;
    private String message;

}

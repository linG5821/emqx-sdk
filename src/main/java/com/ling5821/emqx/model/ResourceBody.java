package com.ling5821.emqx.model;

import lombok.Data;

import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 17:58
 */
@Data
public class ResourceBody {
    private String id;
    private String type;
    private Map<String, Object> config;
    private String description;
}

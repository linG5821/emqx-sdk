package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 17:03
 */
@NoArgsConstructor
@Data
public class Resource {
    private String type;
    private String id;
    private String description;
    private Map<String, Object> config;
}

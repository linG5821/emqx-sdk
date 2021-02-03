package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/2/2 10:05
 */
@NoArgsConstructor
@Data
public class Plugin {

    /**
     * type : auth
     * name : emqx_auth_jwt
     * description : EMQ X Authentication with JWT
     * active : false
     */

    private String type;
//    private String version;
    private String name;
    private String description;
    private Boolean active;
}

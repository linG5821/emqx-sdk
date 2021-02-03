package com.ling5821.emqx.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lsj
 * @date 2021/2/1 10:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientQuery extends PageQuery{
    private String clientId;
    private String username;
    private String zone;
    private String ipAddress;
    private ConnState connState;
    private Boolean cleanStart;
    private Proto protoName;
    private Integer protoVer;
    private String _likeClientid;
    private String _likeUsername;
    private Integer _gteCreatedAt;
    private Integer _lteCreatedAt;
    private Integer _gteConnectedAt;
    private Integer _lteConnectedAt;
}

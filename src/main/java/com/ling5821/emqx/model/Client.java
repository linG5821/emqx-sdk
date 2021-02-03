package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/1/30 2:25
 */

@NoArgsConstructor
@Data
public class Client {

    /**
     * expiry_interval : 0
     * mqueue_dropped : 0
     * ip_address : 10.10.0.1
     * recv_pkt : 1
     * is_bridge : false
     * inflight : 0
     * send_pkt : 4
     * created_at : 2021-02-01 03:34:58
     * proto_ver : 4
     * connected_at : 2021-02-01 03:34:58
     * max_subscriptions : 0
     * max_mqueue : 1000
     * connected : true
     * heap_size : 610
     * max_awaiting_rel : 100
     * proto_name : MQTT
     * recv_oct : 147
     * send_oct : 10
     * awaiting_rel : 0
     * recv_msg : 0
     * max_inflight : 32
     * mailbox_len : 0
     * clientid : c40724e52eaf4b2593e139ac4c2fca52
     * node : emqx@node1.emqx.iot
     * clean_start : true
     * recv_cnt : 7
     * mountpoint : undefined
     * zone : external
     * send_msg : 0
     * send_cnt : 4
     * keepalive : 60
     * subscriptions_cnt : 0
     * reductions : 5666
     * username : mqtt-test-4&cIw19qdeC6z
     * mqueue_len : 0
     * port : 49978
     */

    private Integer expiryInterval;
    private Integer mqueueDropped;
    private String ipAddress;
    private Integer recvPkt;
    private Boolean isBridge;
    private Integer inflight;
    private Integer sendPkt;
    private String createdAt;
    private Integer protoVer;
    private String connectedAt;
    private Integer maxSubscriptions;
    private Integer maxMqueue;
    private Boolean connected;
    private Integer heapSize;
    private Integer maxAwaitingRel;
    private String protoName;
    private Integer recvOct;
    private Integer sendOct;
    private Integer awaitingRel;
    private Integer recvMsg;
    private Integer maxInflight;
    private Integer mailboxLen;
    private String clientid;
    private String node;
    private Boolean cleanStart;
    private Integer recvCnt;
    private String mountpoint;
    private String zone;
    private Integer sendMsg;
    private Integer sendCnt;
    private Integer keepalive;
    private Integer subscriptionsCnt;
    private Integer reductions;
    private String username;
    private Integer mqueueLen;
    private Integer port;
}

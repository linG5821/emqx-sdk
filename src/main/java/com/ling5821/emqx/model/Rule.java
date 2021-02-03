package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 16:08
 */
@NoArgsConstructor
@Data
public class Rule {

    /**
     * rawsql : SELECT
     payload.msg as msg
     FROM
     "hello/#"
     WHERE
     msg = 'hello'
     * on_action_failed : continue
     * metrics : [{"speed_max":0,"speed_last5m":0,"speed":0,"node":"emqx@node2.emqx.iot","matched":0},{"speed_max":0,"speed_last5m":0,"speed":0,"node":"emqx@node1.emqx.iot","matched":0}]
     * id : 1111
     * for : ["hello/#"]
     * enabled : true
     * description :
     * actions : [{"params":{"target_topic":"repub/to/${clientid}","target_qos":1,"payload_tmpl":"${payload}"},"name":"republish","metrics":[{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}],"id":"republish_1612349211431713600","fallbacks":[]},{"params":{},"name":"do_nothing","metrics":[{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}],"id":"do_nothing_1612349211432160700","fallbacks":[]},{"params":{},"name":"inspect","metrics":[{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}],"id":"inspect_1612349211432468000","fallbacks":[]},{"params":{"payload_tmpl":"${payload}","$resource":"resource:112470"},"name":"data_to_webserver","metrics":[{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}],"id":"data_to_webserver_1612349211432765200","fallbacks":[{"params":{"payload_tmpl":"","forward_topic":"","$resource":"resource:545283"},"name":"data_to_mqtt_broker","metrics":[{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}],"id":"data_to_mqtt_broker_1612349211433122600","fallbacks":[]}]},{"params":{"payload_tmpl":"${payload}","forward_topic":"","$resource":"resource:545283"},"name":"data_to_mqtt_broker","metrics":[{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}],"id":"data_to_mqtt_broker_1612349211433489400","fallbacks":[{"params":{"target_topic":"repub/to/${clientid}","target_qos":0,"payload_tmpl":"${payload}"},"name":"republish","metrics":[{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}],"id":"republish_1612349211433822800","fallbacks":[]}]}]
     */

    @SerializedName("rawsql")
    private String rawsql;
    @SerializedName("on_action_failed")
    private String onActionFailed;
    @SerializedName("metrics")
    private List<MetricsBean> metrics;
    @SerializedName("id")
    private String id;
    @SerializedName("for")
    private List<String> forX;
    @SerializedName("enabled")
    private Boolean enabled;
    @SerializedName("description")
    private String description;
    @SerializedName("actions")
    private List<RuleActionBean> actions;

    @NoArgsConstructor
    @Data
    public static class MetricsBean {
        /**
         * speed_max : 0
         * speed_last5m : 0
         * speed : 0
         * node : emqx@node2.emqx.iot
         * matched : 0
         */

        @SerializedName("speed_max")
        private Integer speedMax;
        @SerializedName("speed_last5m")
        private Integer speedLast5m;
        @SerializedName("speed")
        private Integer speed;
        @SerializedName("node")
        private String node;
        @SerializedName("matched")
        private Integer matched;
    }

    @NoArgsConstructor
    @Data
    public static class RuleActionBean {
        /**
         * params : {"target_topic":"repub/to/${clientid}","target_qos":1,"payload_tmpl":"${payload}"}
         * name : republish
         * metrics : [{"success":0,"node":"emqx@node2.emqx.iot","failed":0},{"success":0,"node":"emqx@node1.emqx.iot","failed":0}]
         * id : republish_1612349211431713600
         * fallbacks : []
         */

        @SerializedName("params")
        private ActionParams params;
        @SerializedName("name")
        private String name;
        @SerializedName("metrics")
        private List<ActionMetrics> metrics;
        @SerializedName("id")
        private String id;
        @SerializedName("fallbacks")
        private List<RuleActionBean> fallbacks;

        @NoArgsConstructor
        @Data
        public static class ActionMetrics {
            /**
             * success : 0
             * node : emqx@node2.emqx.iot
             * failed : 0
             */

            @SerializedName("success")
            private Integer success;
            @SerializedName("node")
            private String node;
            @SerializedName("failed")
            private Integer failed;
        }
    }
}

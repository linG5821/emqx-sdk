package com.ling5821;


import cn.hutool.core.io.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ling5821.emqx.EmqxConfig;
import com.ling5821.emqx.EmqxHttpClient;
import com.ling5821.emqx.common.GsonUtils;
import com.ling5821.emqx.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class EmqxClientTest
{
    private EmqxHttpClient client;
    private Gson gson;

    @Before
    public void init() {
        EmqxConfig config = new EmqxConfig();
        config.setAddress("http://127.0.0.1:18083/");
        config.setUsername("admin");
        config.setPassword("public");
        this.client = new EmqxHttpClient(config);
        this.gson = GsonUtils.createGson();
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String clientId = "mqttx_beb40ebf";
        String node2 = "emqx@node2.emqx.iot";
        String node1 = "emqx@node1.emqx.iot";
        String username = "mqtt-test-4&cIw19qdeC6z";
        String plugin = "emqx_lua_hook";
        System.out.println(client.getBrokers());
        System.out.println(client.getBroker(node1));
        System.out.println(client.getNodes());
        System.out.println(client.getNode(node1));
        ClientQuery clientQuery = new ClientQuery();
        clientQuery.setConnState(ConnState.CONNECTED);
        clientQuery.setProtoName(Proto.MQTT);
        System.out.println(client.getClients(clientQuery));
        PageQuery pageQuery = new PageQuery();
        System.out.println(client.getClientsByNode(pageQuery, node1));
        System.out.println(client.getClient(clientId));
        System.out.println(client.getClientByNode(node1, clientId));
        /* 该接口不存在 */
//        System.out.println(client.kickedClientByNode(node1, clientId));
        System.out.println(client.getAclCaches(clientId));
        System.out.println(client.getAclCaches(clientId));
        System.out.println(client.deleteAclCaches(clientId));
        System.out.println(client.getClientsByUsername(username));
        System.out.println(client.getClientsByNodeAndUsername(node1, username));
        SubscriptionQuery query = new SubscriptionQuery();
        query.setQos(Qos.QOS0);
        query.set_match_topic("hello/111");
        System.out.println(client.getSubscriptions(query));
        System.out.println(client.getSubscriptions(clientId));
        System.out.println(client.getSubscriptionsByNode(node1, query));
        System.out.println(client.getSubscriptionsByNodeAndClientId(node1, clientId));

        System.out.println(client.getRoutes(new PageQuery()));
        System.out.println(client.getRoute("hello/111"));
        /* 发布消息 */
        MqttMessageBody message = new MqttMessageBody();
        message.setClientid("test");
        message.setTopic("hello/111");
        message.setEncoding(EncodingType.PLAIN.getValue());
        message.setQos(Qos.QOS2.getValue());
        message.setRetain(false);
        message.setPayload("hello");
        System.out.println(client.publish(message));

        /* 订阅主题 */
        SubscribeBody subscribeBody = new SubscribeBody();
        subscribeBody.setClientid(clientId);
        subscribeBody.setTopic("hello/222");
        subscribeBody.setQos(Qos.QOS2.getValue());
        client.subscribe(subscribeBody);

        /* 解除订阅 */
        UnSubscribeBody unSubscribeBody = new UnSubscribeBody();
        unSubscribeBody.setClientid(clientId);
        unSubscribeBody.setTopic("$share/aaa/hello/111");
        client.unsubscribe(unSubscribeBody);

        /* 批量发布 */
        List<MqttMessageBody> messageBodyList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MqttMessageBody body = new MqttMessageBody();
            body.setClientid("pub");
            body.setTopic("hello/111");
            body.setEncoding(EncodingType.PLAIN.getValue());
            body.setPayload("hello" + i);
            messageBodyList.add(body);
        }
        client.publishBatch(messageBodyList);

        /* 批量订阅 */
        List<SubscribeBody> subscribeBodyList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SubscribeBody body = new SubscribeBody();
            body.setClientid(clientId);
            body.setTopic("hello/3333" + i);
            body.setQos(Qos.QOS1.getValue());
            subscribeBodyList.add(body);
        }
        client.subscribeBatch(subscribeBodyList);

        /* 批量取消订阅 */
        List<UnSubscribeBody> unSubscribeBodyList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UnSubscribeBody body = new UnSubscribeBody();
            body.setClientid(clientId);
            body.setTopic("hello/3333" + i);
            unSubscribeBodyList.add(body);
        }
        System.out.println(client.unsubscribeBatch(unSubscribeBodyList));

        /* 踢出设备 */
        System.out.println(client.kickedClient(clientId));


        /* Plugin*/
        System.out.println(client.getPlugins());
        System.out.println(client.getPluginsByNode(node1));
        client.loadPlugin(node2, plugin);
        client.unloadPlugin(node2, plugin);

        /* 监听器 */
        System.out.println(client.getListeners());
        System.out.println(client.getListenersByNode(node1));

        /* Module */
        System.out.println(client.getModules());
        System.out.println(client.getModulesByNode(node2));
        client.unloadModule("emqx_mod_topic_metrics");
        client.loadModule("emqx_mod_topic_metrics");
        client.unloadModuleByNode(node1, "emqx_mod_topic_metrics");
        client.loadModuleByNode(node1, "emqx_mod_topic_metrics");
        client.reloadModule("emqx_mod_acl_internal");
        client.reloadModuleByNode(node2, "emqx_mod_acl_internal");
        System.out.println(client.getMetrics());
        System.out.println(client.getMetricsByNode(node2));

        /* 开启/关闭主题统计 */
        client.setTopicMetric("hello/111", true);
        client.setTopicMetric("hello/111", false);
        System.out.println(client.getTopicMetrics());
        client.getTopicMetricsByTopic("hello/111");

        /* 状态 */
        System.out.println(client.getStats());
        System.out.println(client.getStatsByNode(node2));

        /* 报警 */
        System.out.println(client.getAlarms());
        System.out.println(client.getAlarmsByNode(node2));
        System.out.println(client.getActivatedAlarms());
        System.out.println(client.getActivatedAlarmsByNode(node2));
        System.out.println(client.getDeactivatedAlarms());
        System.out.println(client.getDeactivatedAlarmsByNode(node2));
//        client.cancelDeactivatedAlarm(new CancelDeactivatedAlarmBody(node2, "high_system_memory_usage"));
//        client.deleteDeactivatedAlarm();
//        client.deleteDeactivatedAlarmByNode(node2);

        /* 黑名单 */
        BannedBody body = new BannedBody();
        body.setWho(clientId);
        body.setAs(BannedType.CLIENT_ID.getValue());
        body.setReason("");
        body.setBy("linG");
        client.addBanned(body);
        System.out.println(client.getBanned(new PageQuery()));
        client.deleteBanned(clientId, BannedType.CLIENT_ID.getValue());
        System.out.println(client.getBanned(new PageQuery()));

        /* 导入导出 */
//        System.out.println(client.exportFile());
//        System.out.println(client.getExportFiles());
//        client.importFile("emqx-export-2021-2-2-6-46-54.json");
//        client.downloadExportFile("emqx-export-2021-2-2-6-46-54.json", "./hello.json");
//        client.uploadFile("./hello.json");
//        client.uploadFile(gson.fromJson(FileUtil.readString("./hello.json", StandardCharsets.UTF_8), UploadFileBody.class));
//        client.deleteFile("emqx-export-2021-2-2-6-46-54.json");

        /* 动作 */
        List<Action> actions = client.getActions();
        for (Action action : actions) {
            Action rpcData = client.getAction(action.getName());
            Assert.assertEquals(gson.toJson(action), gson.toJson(rpcData));
        }

        /* 资源类型 */
        List<ResourceType> resourceTypes = client.getResourceTypes();
        for (ResourceType resourceType : resourceTypes) {
            ResourceType rpcData = client.getResourceType(resourceType.getName());
            Assert.assertEquals(gson.toJson(resourceType), gson.toJson(rpcData));
        }

        /* 资源 */
        List<Resource> resources = client.getResources();
        for (Resource resource : resources) {
            Resource rpcData = client.getResource(resource.getId());
            Assert.assertEquals(gson.toJson(resource), gson.toJson(rpcData));
        }
        ResourceBody resourceBody = new ResourceBody();
        resourceBody.setId("111111");
        resourceBody.setType("bridge_mqtt_sub");
        resourceBody.setDescription("测试2");
        String config = "{\"username\":\"\",\"subscription_opts\":[],\"ssl\":\"off\",\"reconnect_interval\":\"30s\",\"proto_ver\":\"mqttv4\",\"pool_size\":8,\"password\":\"\",\"keyfile\":\"etc/certs/client-key.pem\",\"keepalive\":\"60s\",\"clientid\":\"client\",\"ciphers\":\"ECDHE-ECDSA-AES256-GCM-SHA384,ECDHE-RSA-AES256-GCM-SHA384\",\"certfile\":\"etc/certs/client-cert.pem\",\"cacertfile\":\"etc/certs/cacert.pem\",\"append\":true,\"address\":\"127.0.0.1:1883\"}";
        resourceBody.setConfig(gson.fromJson(config, new TypeToken<Map<String, Object>>(){}.getType()));
        Resource resource = client.addResource(resourceBody);
        client.deleteResource(resource.getId());

        /* 规则 */
        RuleBody ruleBody = new RuleBody();
        ruleBody.setId("hello2222");
        ruleBody.setDescription("测试");
        ruleBody.setRawsql("SELECT payload.msg as msg FROM \"hello/#\" where msg = 'hello' ");
        List<RuleBody.RuleActionBody> actionBeans = new ArrayList<>();
        RuleBody.RuleActionBody ruleActionBody = new RuleBody.RuleActionBody();
        ActionParams params = new ActionParams();
        /**
         * "target_topic": "repub/to/${clientid}",
         * "target_qos": 1,
         * "payload_tmpl": "${payload}"
         */
        params.setTargetTopic("repub/to/${clientid}");
        params.setTargetQos(1);
        params.setPayloadTmpl("${payload}");
        ruleActionBody.setParams(params);
        ruleActionBody.setName("republish");
        actionBeans.add(ruleActionBody);
        RuleBody.RuleActionBody fall = gson.fromJson(gson.toJson(ruleActionBody), RuleBody.RuleActionBody.class);
        ruleActionBody.setFallbacks(Collections.singletonList(fall));
        ruleBody.setActions(actionBeans);
        Rule ruleAdd = client.addRule(ruleBody);
        List<Rule> rules = client.getRules();
        for (Rule rule : rules) {
            Rule rpcData = client.getRule(rule.getId());
            System.out.println(rpcData);
        }
        ruleBody.setDescription("测试update");
        client.updateRule(ruleBody);
        client.deleteRule(ruleAdd.getId());



        /* 数据遥测 需要启用插件才可以访问接口 */
        client.setTelemetryStatus(true);
        System.out.println(client.getTelemetryStatus());
        System.out.println(client.getTelemetryData());

    }
}

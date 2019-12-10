package me.zhengjie.utils.kafkaconfig;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: 张政淇
 * Date: 2019/4/2
 * Time: 13:26
 * Progect: StreamSimulation
 */
public class KafkaConfig {
    public static Properties getProducerProp() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.5.83.175:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
    public static Properties getConsummerProp() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "10.5.83.175:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("auto.offset.reset", "latest");
        return props;
    }
}

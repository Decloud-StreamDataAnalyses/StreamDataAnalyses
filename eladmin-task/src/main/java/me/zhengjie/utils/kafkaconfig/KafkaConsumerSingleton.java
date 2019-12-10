package me.zhengjie.utils.kafkaconfig;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerSingleton {
    private static KafkaConsumer<String, String> consumer = null;


    public static KafkaConsumer<String, String> getConsumer(String topic) {
        if (consumer == null) {
            synchronized (KafkaConsumer.class) {
                if (consumer == null) {
                    Properties props = KafkaConfig.getConsummerProp();
                    consumer = new KafkaConsumer<>(props);
                    System.out.println("链接被销毁，重新创建连接。");
                }
            }
        }
        consumer.subscribe(Collections.singletonList(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                consumer.seekToBeginning(partitions);
//                for (TopicPartition topicPartition : partitions) {
//                    consumer.seek(topicPartition, 10010);
//                }

            }
        });
        return consumer;
    }
}

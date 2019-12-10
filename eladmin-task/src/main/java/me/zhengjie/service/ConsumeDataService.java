package me.zhengjie.service;

import me.zhengjie.utils.kafkaconfig.KafkaConsumerSingleton;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumeDataService {


    public List<String> consume(String topic) {
        KafkaConsumer<String, String> consumer = KafkaConsumerSingleton.getConsumer(topic);

        ConsumerRecords<String, String> records = consumer.poll(100);

        List<String> res = new ArrayList<>();
        for (ConsumerRecord<String, String> record : records) {
            res.add(record.value());
//            System.out.printf("offset = %d, topic = %s, value = %s%n", record.offset(), record.topic(), record.value());
        }
        return res;


//        List<String> timestamp = new ArrayList<>();
//        List<Double> value = new ArrayList<>();
//        List<String> label = new ArrayList<>();
//        for (ConsumerRecord<String, String> record : records) {
//            System.out.printf("offset = %d, topic = %s, value = %s%n", record.offset(), record.topic(), record.value());
//            String[] words = record.value().split(",");
//            timestamp.add(words[0]);
//            value.add(Double.valueOf(words[1]));
////            label.add(words[2]);
//        }
//        return new EchartData1(timestamp, value, label);
    }
}

package me.zhengjie.rest;

import me.zhengjie.service.ConsumeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * title: GetDataFromKafkaController
 * projectName： kafka-flink-web
 * author： 张政淇
 * date： 2019/11/19
 * time： 14:17
 */
@RestController
@RequestMapping("/api/rest")
public class GetDataFromKafkaController {
    @Autowired
    ConsumeDataService consumeDataService;

    @RequestMapping(value = "/{topic}/result")
    public List<String> getCityListByCountryId(@PathVariable("topic") String topic) {
        System.out.println("RESTRESTRESTREST..................");
        return consumeDataService.consume(topic);
    }

}

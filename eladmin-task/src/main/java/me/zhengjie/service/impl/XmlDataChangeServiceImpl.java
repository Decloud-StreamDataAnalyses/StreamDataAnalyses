package me.zhengjie.service.impl;

import me.zhengjie.service.XmlDataChangeService;
import org.springframework.stereotype.Service;

/**
 * xml数据处理实现层
 * 将前端传入的xml字符串中的**进行处理
 */
@Service
public class XmlDataChangeServiceImpl implements XmlDataChangeService {


    @Override
    public String xmlToFile(String str) {
        String data = str.replace("$$","\n");
        return data;
    }

    @Override
    public String xmlToMap(String str) {
//        System.out.println(str);
        String data = str.replace("$","");
        return data;
    }
}

package me.zhengjie.service.impl;

import me.zhengjie.pojo.Operator;
import me.zhengjie.service.XmlToCodeService;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static me.zhengjie.utils.XmlAnalysis.operatorsAnalysis;
import static me.zhengjie.utils.mapcode.MapCode.tasksplit;

@Service
public class XmlToCodeServiceImpl implements XmlToCodeService {
//    @Override
//    public String xmlToCode(String xmlstr) throws IOException, SAXException, ParserConfigurationException {
//        List<Operator> operatorList = new ArrayList<Operator>();
////        operatorList = operatorsAnalysis("D:\\test\\MyXML\\labelEncoder.xml");
//        operatorList = operatorsAnalysis(xmlstr);
//
//        System.out.println("------------------------------------===-------------------------");
//        String code = tasksplit("ss",operatorList);
//        System.out.println("===================List<Operator>=============================================");
//        System.out.println(code);
//
//        return code;
////        return operatorList;
//    }

    @Override
    public List<Operator> xmlToList(String xmlstr) throws IOException, SAXException, ParserConfigurationException {
        List<Operator> operatorList = new ArrayList<Operator>();
//        operatorList = operatorsAnalysis("D:\\test\\MyXML\\labelEncoder.xml");
        operatorList = operatorsAnalysis(xmlstr);

        return operatorList;
    }

    @Override
    public String listToCode(List<Operator> operators) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("------------------------------------===-------------------------");
        String code = tasksplit("ss",operators);
        System.out.println("===================List<Operator>=============================================");
        System.out.println(code);

        return code;
    }


}

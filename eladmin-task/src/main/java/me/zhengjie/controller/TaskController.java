package me.zhengjie.controller;

import me.zhengjie.pojo.Operator;
import me.zhengjie.pojo.RESTPojo;
import me.zhengjie.service.CodeToRunService;
import me.zhengjie.service.CreatXmlService;
import me.zhengjie.service.XmlDataChangeService;
import me.zhengjie.service.XmlToCodeService;
import me.zhengjie.utils.XmlAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    CreatXmlService creatXmlService;

    @Autowired
    XmlDataChangeService xmlDataChangeService;

    @Autowired
    XmlToCodeService xmlToCodeService;

    @Autowired
    CodeToRunService codeToRunService;

    @ResponseBody
    @RequestMapping("/creatXml")
    //传入xml字符串
    public List<String> XmlToTask(String str) throws Exception {
//        System.out.println(str);
        String xmlToMap=xmlDataChangeService.xmlToMap(str); //这里返回的就是将xml中**变成空格的字符串
  //      System.out.println(xmlToMap);

        String xmlToFile=xmlDataChangeService.xmlToFile(str);
        System.out.println(xmlToFile);//这里返回的就是将xml中的**变成\n的字符串

        creatXmlService.createxmlfile(xmlToFile);

//        HashMap<String, String> map = new HashMap<>();
        List<Operator> operatorList = xmlToCodeService.xmlToList(xmlToMap);
        String code = xmlToCodeService.listToCode(operatorList);
        codeToRunService.codeToRun(code);
        List<RESTPojo> restPojoList = XmlAnalysis.getRest(operatorList);
//        System.out.println("restPojoList"+restPojoList);
        List<String> restAPIs = new ArrayList<>();
        List<String> restAPIsToUI = new ArrayList<>();
        for (RESTPojo data : restPojoList) {
            restAPIs.add("/{" + data.getTopic() + "}/result");
            restAPIsToUI.add("10.5.83.176:8089/" + data.getTopic() + "/result");
        }
        System.out.println("restAPIs:"+restAPIsToUI);
        return restAPIsToUI;
    }
}

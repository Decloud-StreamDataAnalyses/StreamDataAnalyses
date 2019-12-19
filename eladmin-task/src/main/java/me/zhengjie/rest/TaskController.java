package me.zhengjie.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.pojo.Operator;
import me.zhengjie.pojo.RESTPojo;
import me.zhengjie.service.CodeToRunService;
import me.zhengjie.service.CreatXmlService;
import me.zhengjie.service.XmlDataChangeService;
import me.zhengjie.service.XmlToCodeService;
import me.zhengjie.utils.XmlAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@Api(tags = "接受xml，提交任务，返回REST的controller")
public class TaskController {



    @Autowired
    CreatXmlService creatXmlService;

    @Autowired
    XmlDataChangeService xmlDataChangeService;

    @Autowired
    XmlToCodeService xmlToCodeService;

    @Autowired
    CodeToRunService codeToRunService;



    @PostMapping(value = "/postTest")
    public String postTest(String xmlStr){
        System.out.println(xmlStr);
        return "{\"jobs\":\"111111111111\"}";
    }

    @PostMapping(value = "/xmlToTask")
    @ApiOperation("接收xml，执行任务，返回REST")
    //传入xml字符串
    public List<String> XmlToTask(String xmlStr) throws Exception {
//        System.out.println(str);
//        String xmlToMap=xmlDataChangeService.xmlToMap(xmlStr); //这里返回的就是将xml中**变成空格的字符串
  //      System.out.println(xmlToMap);

//        String xmlToFile=xmlDataChangeService.xmlToFile(xmlStr);
//        System.out.println(xmlToFile);//这里返回的就是将xml中的**变成\n的字符串

//        creatXmlService.createxmlfile(xmlToFile);

//        HashMap<String, String> map = new HashMap<>();
        System.out.println(xmlStr);
        List<Operator> operatorList = xmlToCodeService.xmlToList(xmlStr);
        String code = xmlToCodeService.listToCode(operatorList);
        codeToRunService.codeToRun(code);
        List<RESTPojo> restPojoList = XmlAnalysis.getRest(operatorList);
//        System.out.println("restPojoList"+restPojoList);
        List<String> restAPIs = new ArrayList<>();
        List<String> restAPIsToUI = new ArrayList<>();
        for (RESTPojo data : restPojoList) {
            restAPIs.add("/{" + data.getTopic() + "}/result");
            restAPIsToUI.add("http://10.5.83.176:8000/api/rest/" + data.getTopic() + "/result");
        }
        System.out.println("restAPIs:"+restAPIsToUI);
        return restAPIsToUI;
    }
}

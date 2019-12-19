package me.zhengjie.utils.mapcode;

import me.zhengjie.pojo.Operator;
import me.zhengjie.pojo.OperatorObject;
import me.zhengjie.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapCode {

    public  static String tasksplit(String xml, List<Operator> operatorList)
            throws IOException, SAXException, ParserConfigurationException {

        String code = "";
        String preCode = "";
        String mainCode = Constants.MAINSTART;
        code += Constants.IMPORTPKG;
        List<String> tasks = new ArrayList<String>();
        String codeXmlStr = "eladmin-task\\src\\main\\resources\\code.xml";
        // 读 code.xml
        Map<String, OperatorObject> operatorObjectMap = parseconfigxml(codeXmlStr);
        //将整个字符串分割为若干个小的任务，采用的是树遍历，前后合并

        for (Operator op:operatorList) {
            mainCode += maptask("ss",op,operatorObjectMap).get("mainCode");
            preCode += maptask("ss",op,operatorObjectMap).get("preCode");
        }
        code += preCode;
        code += mainCode;
        code += Constants.MAINEND;
//        System.out.println(code);

        return code;
    }

    /**
     * 输入原始字符串，最小任务集
     * @return Flink任务代码
     */
    public static Map<String, String> maptask(String xml, Operator operator,
                                              Map<String, OperatorObject> operatorObjectMap){

        Map<String, String> codeMap = new HashMap<>();
        String operatorCode = "";
        String ipport = "";
        //code += main

        //for in operators
        // ClassMap[op.class]
        //switch op.class:

        //case 'Filter':
        //case:
        //default:

        // 第一个是Filter
//        System.out.println("=====:"+operator.getNodeClass());

        OperatorObject operator1 = new OperatorObject();
        operator1.setPrecode("testPrecode\n");
        operator1.setMaincode("testMaincode\n");
        String preCode = operatorObjectMap.getOrDefault(operator.getNodeClass(),operator1).getPrecode();
        String mainCode = operatorObjectMap.getOrDefault(operator.getNodeClass(),operator1).getMaincode();
        codeMap.putAll(paraChange(preCode, mainCode, operator));

        return  codeMap;
    }

    public static Map<String,String> paraChange(String preCode, String mainCode, Operator operator){
        HashMap<String, String> codeMap = new HashMap<>();

        for (String key:operator.getParameters().keySet()
        ) {
            String para = operator.getParameters().get(key);
            String keyInCode = "%"+key+"%";
            while(preCode.contains(keyInCode))
                preCode = preCode.replaceAll(keyInCode,operator.getParameters().get(key));

            while(mainCode.contains(keyInCode))
                mainCode = mainCode.replaceAll(keyInCode,operator.getParameters().get(key));

            codeMap.put("preCode",preCode);
            codeMap.put("mainCode",mainCode);
        }

        return codeMap;
    }

    public static Map<String, OperatorObject> parseconfigxml(String operatorpath) throws ParserConfigurationException, IOException, SAXException {

        Map<String, OperatorObject> operatorObjectMap = new HashMap<>();

        //1 解析xml
        File file = new File(operatorpath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        Element element = document.getDocumentElement();

        NodeList codeNodes = element.getElementsByTagName("code");
//        System.out.println(codeNodes.getLength());
        for(int i=0;i<codeNodes.getLength();i++){
            Element operatorsElement = (Element) codeNodes.item(i);
            NodeList operatorNodes = operatorsElement.getElementsByTagName("Operator");
//            System.out.println(operatorNodes.getLength());
            for (int j = 0; j < operatorNodes.getLength(); j++) {
                Element operatorElement = (Element) operatorNodes.item(j);
                OperatorObject operatorObject = new OperatorObject();
                operatorObject.setPrecode(operatorElement.getElementsByTagName("Precode")
                        .item(0).getTextContent());
                operatorObject.setMaincode(operatorElement.getElementsByTagName("Maincode")
                        .item(0).getTextContent());
                NodeList parameterNodes = operatorElement.getElementsByTagName("Parameter");
                HashMap<String, String> paraMap = new HashMap<String, String>();
                for (int k = 0; k < parameterNodes.getLength(); k++) {
                    Element parameter = (Element)parameterNodes.item(k);
                    paraMap.put(parameter.getAttribute("key"),parameter.getAttribute("value"));
                }
                operatorObject.setParamtermap(paraMap);
                operatorObjectMap.put(operatorElement.getAttribute("class"),operatorObject);
            }
        }


        return operatorObjectMap;
    }



}

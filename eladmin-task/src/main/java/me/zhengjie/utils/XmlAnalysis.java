package me.zhengjie.utils;

import me.zhengjie.pojo.Operator;
import me.zhengjie.pojo.RESTPojo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *   解析xml文件 分别返回 operatorList 和 connectList
 */

public class XmlAnalysis {
//    public static List<Operator> operatorsAnalysis(String xmlAddr)
    public static List<Operator> operatorsAnalysis(String xml)
            throws ParserConfigurationException, IOException, SAXException {
//        File file = new File(xmlAddr);
        InputStream is = new ByteArrayInputStream(xml.getBytes());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        Element element = document.getDocumentElement();

        List<Operator> operatorList = new ArrayList<Operator>();

        NodeList processNodes = element.getElementsByTagName("process");
        Element processElement = (Element) processNodes.item(0);
        String taskName = processElement.getAttribute("name");
        System.out.println("process_length:"+processNodes.getLength());

        for(int i=0;i<processNodes.getLength();i++){
//                People people = new People();
            Element peopleElement = (Element) processNodes.item(i);
            NodeList operatorNodes = peopleElement.getElementsByTagName("operator");
            System.out.println("operator_length:"+operatorNodes.getLength());
            NodeList connectorNodes = peopleElement.getElementsByTagName("connector");
            System.out.println("connector_length:"+connectorNodes.getLength());

            // 存放当前的长度和参数列表
            String currentPara = "";
            String cuurentLen = "";
            String nextPara = "";
            String nextLen = "";

            // 开始解析 operator
            for (int j = 0; j < operatorNodes.getLength(); j++) {

                Element operatorElement = (Element) operatorNodes.item(j);
                Operator operator = new Operator();
//                    operator.setHeight(Integer.valueOf(operatorElement.getAttribute("height")));
//                    operator.setWidth(Integer.valueOf(operatorElement.getAttribute("width")));
//                    operator.setX(Integer.valueOf(operatorElement.getAttribute("x")));
//                    operator.setY(Integer.valueOf(operatorElement.getAttribute("y")));
                operator.setNodeClass(operatorElement.getAttribute("class"));
                operator.setNodeName(operatorElement.getAttribute("name"));

                NodeList parameterNodes = operatorElement.getElementsByTagName("parameter");
                HashMap<String, String> paraMap = new HashMap<String, String>();

//                    System.out.println(parameterNodes.getLength());
//                    System.out.println(operator);
                for (int k = 0; k < parameterNodes.getLength(); k++) {
                    Element parameter = (Element)parameterNodes.item(k);
                    paraMap.put(parameter.getAttribute("key"),parameter.getAttribute("value"));
                }
                paraMap.put("name",operator.getNodeName());

                // 数据源的模块 要传入参数类型parameters
                if(operator.getNodeClass().equals("SourceTopic")){
                    String[] strings = paraMap.get("parameters").split(",");
                    currentPara = paraMap.get("parameters");
                    cuurentLen = String.valueOf(strings.length);
                    nextLen = cuurentLen;
                    nextPara = currentPara;
                    paraMap.put("cuurentLen",cuurentLen);
                    paraMap.put("currentPara",currentPara);
//                    System.out.println(operator);
//                    paraMap.put("length2",String.valueOf(strings.length-1));
                }

                int dsindex = 1;
                String unionDatasources = "";
                boolean isFirstSource = true;
                //遍历 connector
                for (int k = 0; k < connectorNodes.getLength(); k++) {
                    Element connectorElement = (Element) connectorNodes.item(k);
                    if(connectorElement.getAttribute("to_name").equals(operator.getNodeName())){
                        String sourceName = connectorElement.getAttribute("from_name");
                        paraMap.put("datasource"+dsindex,sourceName);

                        for (Operator op:operatorList) {
                            if(op.getNodeName().equals(sourceName)){
//                                System.out.println("datasource"+dsindex+"Para"+ op.getParameters().get("currentPara"));
                                paraMap.put("datasource"+dsindex+"Para", op.getParameters().get("nextPara"));
                                paraMap.put("datasource"+dsindex+"Len", op.getParameters().get("nextLen"));
                            }
                        }
                        dsindex++;
                        if(operator.getNodeClass().equals("Union")){
                            if(unionDatasources.length()==0 && isFirstSource){
                                isFirstSource = false;
                                continue;
                            }
                            else
                                unionDatasources += (connectorElement.getAttribute("from_name")+",");
                        }
                    }
                }


                if(operator.getNodeClass().equals("StreamHashConnect")){
//                    cuurentLen = "3";
//                    currentPara = "Long,String,Long";
                    nextLen = "4";
                    nextPara = "Long,String,String,Long";
                }

                // 删除列操作
                if(operator.getNodeClass().equals("DeleteColumn")){
                    nextLen = String.valueOf(Integer.valueOf(paraMap.get("datasource1Len"))-1);
                    int index = Integer.valueOf(paraMap.get("index"));
                    String[] paras = paraMap.get("datasource1Para").split(",");
                    nextPara = "";
                    for (int k = 0; k < paras.length; k++) {
                        if(k!=index){
                            if(nextPara.length()==0)
                                nextPara+=paras[k];
                            else
                                nextPara+= (","+paras[k]);
                        }
                    }
//                    paraMap.put("functionName","delete"+operator.getNodeName());
                }

                if(operator.getNodeClass().equals("AnomalyDetection")){
                    nextPara = paraMap.get("datasource1Para") + ",String";
                    nextLen = String.valueOf(Integer.valueOf(paraMap.get("datasource1Len"))+1);
                }

                if(operator.getNodeClass().equals("FlowFullJoin")){
                    String datasource1Para = paraMap.get("datasource1Para");
                    String datasource2Para = paraMap.get("datasource2Para");
                    nextPara = datasource1Para;
                    String[] para2 = datasource2Para.split(",");
                    for (int k = 1; k < Integer.valueOf(Integer.valueOf(paraMap.get("datasource2Len"))); k++) {
                        nextPara += ","+para2[k];
                    }

                    nextLen = String.valueOf(Integer.valueOf(paraMap.get("datasource1Len"))+Integer.valueOf(paraMap.get("datasource2Len"))-1);
                }

                if(operator.getNodeClass().equals("LabelEncoder")){
                    int index = Integer.valueOf(paraMap.get("index"));
                    String[] paras = paraMap.get("datasource1Para").split(",");
                    for (String s:paras) {
                        System.out.println(s);
                    }

                    paras[index] = "Long";
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(paras[0]);
                    for (int k = 1; k < paras.length; k++) {
                        stringBuffer.append(","+paras[k]);
                    }
                    nextPara = stringBuffer.toString();
                }

                if(operator.getNodeClass().equals("MinMaxScaler")){
                    int index = Integer.valueOf(paraMap.get("index"));
                    String[] paras = paraMap.get("datasource1Para").split(",");
                    paras[index] = "Double";
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(paras[0]);
                    for (int k = 1; k < paras.length; k++) {
                        stringBuffer.append(","+paras[k]);
                    }
                    nextPara = stringBuffer.toString();
                }

                if(operator.getNodeClass().equals("FlowInnerJoin")){
                    System.out.println(paraMap);
                    nextLen = String.valueOf(Integer.valueOf(paraMap.get("datasource1Len"))
                            +Integer.valueOf(paraMap.get("datasource2Len"))-1);
                    String datasource1Para = paraMap.get("datasource1Para");
                    String datasource2Para = paraMap.get("datasource2Para");
                    String[] para2 = datasource2Para.split(",");
                    int datasource1KeyIndex = Integer.valueOf(paraMap.get("datasource1KeyIndex"));
                    int datasource2KeyIndex = Integer.valueOf(paraMap.get("datasource2KeyIndex"));
                    nextPara = datasource1Para;
                    for (int k = 0; k < Integer.valueOf(Integer.valueOf(paraMap.get("datasource2Len"))); k++) {
                        if(k != datasource2KeyIndex)
                            nextPara += ","+para2[k];
                    }
                    paraMap.put("nextLen", nextLen);
                    paraMap.put("nextPara", nextPara);
                }

                paraMap.put("nextLen",nextLen);
                paraMap.put("nextPara",nextPara);

                if(operator.getNodeClass().equals("REST")){
                    paraMap.put("ip", "10.5.83.175");
                    paraMap.put("port", "9092");

                }

                if(operator.getNodeClass().equals("Union"))
                    paraMap.put("unionSource",unionDatasources.substring(0, unionDatasources.length()-1));

                if(operator.getNodeClass().equals("Split")){
                    String stream = "";
                    int amount = Integer.valueOf(paraMap.get("amount"));
                    for (int k = 2; k <= amount; k++) {
                        stream += "else if(value.f"+paraMap.get("index")+".equals(\"%Stream" + k
                                + "%\"))\n" +
                                "                        list.add(\"%Stream" + k
                                + "%\");\n";
                    }
                    paraMap.put("aStream", stream);
                }

                operator.setParameters(paraMap);
                operatorList.add(operator);
//                System.out.println(operator);
            }
            HashMap<String, String> tasknameMap = new HashMap<>();
            tasknameMap.put("taskname", taskName);
            operatorList.add(new Operator("TaskName", "TaskName1",tasknameMap ));
            for (Operator op:operatorList) {
                System.out.println(op);
            }
//            System.out.println(operatorList);
        }

        return operatorList;
    }

    public static List<RESTPojo> getRest(List<Operator> operators){
        ArrayList<RESTPojo> restPojos = new ArrayList<>();
//        System.out.println("operators"+operators.size());
        for (int i = 0; i < operators.size(); i++) {
            Operator operator = operators.get(i);
//            System.out.println("operatorName:"+operator.getNodeClass());
            if(operator.getNodeClass().equals("REST")){
                RESTPojo restPojo = new RESTPojo();
                restPojo.setIp(operator.getParameters().get("ip"));
                restPojo.setPort(operator.getParameters().get("port"));
                restPojo.setTopic(operator.getParameters().get("topic"));
//                restPojo.setName(operator.getParameters().get("name"));
                restPojos.add(restPojo);
//                System.out.println("restPojo:"+restPojo);
            }
        }
        return restPojos;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        operatorsAnalysis("D:\\test\\MyXML\\labelEncoder.xml");
    }
}

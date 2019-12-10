package me.zhengjie.service;

import me.zhengjie.pojo.Operator;
import me.zhengjie.pojo.OperatorObject;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface XmlToCodeService {

    List<Operator> xmlToList(String xmlstr) throws IOException, SAXException, ParserConfigurationException;

    String listToCode(List<Operator> operators) throws ParserConfigurationException, SAXException, IOException;

}

package me.zhengjie.service.impl;

import me.zhengjie.service.CreatXmlService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;

@Service
public class CreatXmlServiceImpl implements CreatXmlService {

    @Override
    public int createxmlfile(String strxml) {
        String str = strxml;
        String fileAddr;
        String osName = System.getProperties().getProperty("os.name");
        if(osName.contains("Windows"))
            fileAddr = "D:\\test\\MyXML\\data.xml";
        else
            fileAddr = "/home/user802/bigdata/MyXML/data.xml";
        try (
                FileWriter fw = new FileWriter(fileAddr)) {
                fw.write(str);
                fw.flush();
                fw.close();
        } catch (Exception e) {

        }
        return 1;
    }
}

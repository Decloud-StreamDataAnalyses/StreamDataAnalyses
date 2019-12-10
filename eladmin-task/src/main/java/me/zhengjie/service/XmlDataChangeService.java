package me.zhengjie.service;

/**
 * 这个接口是将前端携带**的xml数据进行转义
 * 两个方法  一个方法将**转义为\n  另一个转义为空格
 */
public interface XmlDataChangeService {

    /**
     * 转义为\n 为了可以生成文件
     * @param str
     * @return
     */
    String xmlToFile(String str);

    /**
     * 转义为空格  为了后面的解析映射
     * @param str
     * @return
     */
    String xmlToMap(String str);

}

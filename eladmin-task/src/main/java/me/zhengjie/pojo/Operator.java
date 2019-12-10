package me.zhengjie.pojo;

import java.util.HashMap;
import java.util.Map;

public class Operator {

    private String nodeClass;
    private String nodeName;
//    private int height;
//    private int width;
//    private int x;
//    private int y;


    public Operator() {
    }

    public Operator(String nodeClass, String nodeName, HashMap<String, String> parameters) {
        this.nodeClass = nodeClass;
        this.nodeName = nodeName;
        this.parameters = parameters;
    }

    private HashMap<String ,String> parameters;

    public String getNodeClass() {
        return nodeClass;
    }

    public void setNodeClass(String nodeClass) {
        this.nodeClass = nodeClass;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

//    public int getHeight() {
//        return height;
//    }
//
//    public void setHeight(int height) {
//        this.height = height;
//    }
//
//    public int getWidth() {
//        return width;
//    }
//
//    public void setWidth(int width) {
//        this.width = width;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "nodeClass='" + nodeClass + '\'' +
                ", nodeName='" + nodeName + '\'' +
//                ", height=" + height +
//                ", width=" + width +
//                ", x=" + x +
//                ", y=" + y +
                ", parameters=" + parameters +
                '}';
    }
}

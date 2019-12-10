package me.zhengjie.pojo;

import java.util.Map;

public class OperatorObject {

    private String precode;
    private String maincode;
    private Map<String, String> paramtermap;

    public String getPrecode() {
        return precode;
    }

    public void setPrecode(String precode) {
        this.precode = precode;
    }

    public String getMaincode() {
        return maincode;
    }

    public void setMaincode(String maincode) {
        this.maincode = maincode;
    }

    public Map<String, String> getParamtermap() {
        return paramtermap;
    }

    public void setParamtermap(Map<String, String> paramtermap) {
        this.paramtermap = paramtermap;
    }

    @Override
    public String toString() {
        return "OperatorObject{" +
                "precode='" + precode + '\'' +
                ", maincode='" + maincode + '\'' +
                ", paramtermap=" + paramtermap +
                '}';
    }
}

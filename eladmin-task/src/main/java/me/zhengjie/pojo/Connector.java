package me.zhengjie.pojo;

public class Connector {
    String fromOp;
    String toOp;

    @Override
    public String toString() {
        return "Connector{" +
                "fromOp='" + fromOp + '\'' +
                ", toOp='" + toOp + '\'' +
                '}';
    }

    public String getFromOp() {
        return fromOp;
    }

    public void setFromOp(String fromOp) {
        this.fromOp = fromOp;
    }

    public String getToOp() {
        return toOp;
    }

    public void setToOp(String toOp) {
        this.toOp = toOp;
    }


}

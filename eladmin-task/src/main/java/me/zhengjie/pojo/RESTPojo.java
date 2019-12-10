package me.zhengjie.pojo;

public class RESTPojo {

    String ip;
    String port;
    String topic;


    public RESTPojo() { }

    public RESTPojo(String ip, String port, String topic) {
        this.ip = ip;
        this.port = port;
        this.topic = topic;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


}

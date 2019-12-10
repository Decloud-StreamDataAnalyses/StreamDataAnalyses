package me.zhengjie.model;

import java.util.List;

public class EchartData1 {
    List<String> timestamp;
    List<Double> value;
    List<String> label;
    public EchartData1(List<String> timestamp, List<Double> value, List<String> label) {
        this.timestamp = timestamp;
        this.value = value;
        this.label = label;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<String> timestamp) {
        this.timestamp = timestamp;
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }
}

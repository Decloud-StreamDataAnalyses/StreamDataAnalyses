package me.zhengjie.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClusterMonitor {

    public String name ;

    public String startTime;

    public String duration;

    public String jid;

    public String state;

}

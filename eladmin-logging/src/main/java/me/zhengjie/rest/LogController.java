package me.zhengjie.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.service.LogService;
import me.zhengjie.service.dto.LogQueryCriteria;
import me.zhengjie.utils.SecurityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@RestController
@RequestMapping("/api/logs")
@Api(tags = "监控：日志管理")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check()")
    public void download(HttpServletResponse response, LogQueryCriteria criteria) throws IOException {
        System.out.println("导出数据");
        logService.download(logService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("日志查询")     // 查询日志的功能
    @PreAuthorize("@el.check()")
    public ResponseEntity getLogs(LogQueryCriteria criteria, Pageable pageable){
        System.out.println("日志查询..........");
        criteria.setLogType("INFO");
        return new ResponseEntity<>(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @PostMapping(value = "/postTest")
    @ApiOperation("post测试")     // post测试
    @PreAuthorize("@el.check()")
    public String postTest(String name){
        System.out.println("post测试..........");
        System.out.println(name);
//        criteria.setLogType("INFO");
        return "{\"jobs\":\"1\"}";
//        return new ResponseEntity<>("收到啦～～～", HttpStatus.OK);
    }

    public static String LongToTime(String longTime){
        Long l = Long.parseLong(longTime)/1000;
        String seconds = String.valueOf(l%60);
        String mins = String.valueOf(l/60%60);
        String hours = String.valueOf(l/60/60%24);
        String days = String.valueOf(l/60/60/24);
        StringBuilder sb = new StringBuilder();
        if(!days.equals("0"))
            return sb.append(days+"天 "+hours+"小时 "+mins+"分钟 "+seconds+"秒").toString();
        else if(!hours.equals("0"))
            return sb.append(hours+"小时 "+mins+"分钟 "+seconds+"秒").toString();
        else if(!mins.equals("0"))
            return sb.append(mins+"分钟 "+seconds+"秒").toString();
        else
            return sb.append(seconds+"秒").toString();
    }

    @GetMapping(value = "/cluster")
    @ApiOperation("集群监控")     // 查询日志的功能
    @PreAuthorize("@el.check()")
    public ResponseEntity getCluster(LogQueryCriteria criteria){
        System.out.println("集群监控..........");

        criteria.setLogType("INFO");


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://10.5.83.175:8081/jobs/overview");
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println("----------");
                String result = EntityUtils.toString(entity, "UTF-8");
                System.out.println(result);
                jsonObject = JSONObject.parseObject(result);
//                for (Object jo: arr
//                     ) {
//                    System.out.println(jo);
//                }
                JSONArray jsonArray = jsonObject.getJSONArray("jobs");
                ArrayList<ClusterMonitor> clusterMonitors = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    System.out.println(jsonArray.getJSONObject(i));
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    //(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object.getString("start-time")))
                    String st = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(object.getString("start-time")))));
//                    Date date = new Date();
//                    date.setTime(Long.parseLong(object.getString("duration")));
                    String duration = LongToTime(object.getString("duration"));
//                    String duration = (new SimpleDateFormat("HH:mm:ss").format(new Date(Long.parseLong(object.getString("duration")))));
                    String jid = object.getString("jid");
                    String state = object.getString("state");
                    ClusterMonitor clusterMonitor = new ClusterMonitor(name, st, duration, jid, state);
                    clusterMonitors.add(clusterMonitor);
                }

                PageHelper.startPage(1, 10);
                PageInfo<ClusterMonitor> pageInfo = new PageInfo<ClusterMonitor>(clusterMonitors);
                System.out.println(pageInfo);
                return new ResponseEntity<>(pageInfo, HttpStatus.OK);
            }
            httpGet.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public ResponseEntity getUserLogs(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("INFO");
        criteria.setBlurry(SecurityUtils.getUsername());
        return new ResponseEntity<>(logService.queryAllByUser(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @ApiOperation("错误日志查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity getErrorLogs(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("ERROR");
        return new ResponseEntity<>(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity getErrorLogs(@PathVariable Long id){
        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
    }
}

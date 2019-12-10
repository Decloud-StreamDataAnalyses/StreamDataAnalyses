package me.zhengjie;

import me.zhengjie.domain.Log;
import me.zhengjie.repository.LogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppRun.class)
public class TestLog {

    @Resource
    private LogRepository logRepository;

    @Test
    public void testLog(){
        Log log = new Log();
     //   System.out.println(logRepository.findById(17042l));
//        System.out.println(logRepository.findBylog_type("ERROR"));
        //System.out.println(logRepository.findBybrowser("Firefox 7").size());//
       // System.out.println(logRepository.findBybrowser("Firefox 7").get(1));//
        System.out.println("====================");
        System.out.println(logRepository.findAll());
    }
}

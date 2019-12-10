package me.zhengjie.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Entity //事实上，创建一个Entity Bean对象相当于新建一条记录，删除一个Entity Bean会同时从数据库中删除对应记录，
        // 修改一个Entity Bean时，容器会自动将Entity Bean的状态和数据库同步。
@Data   // lombok 会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，
        // 如为final属性，则不会为该属性生成setter方法。
@Table(name = "log")
@NoArgsConstructor
public class Log  implements Serializable {

    @Id  //@Id注释指定表的主键，它可以有多种生成方式：
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 操作用户
    private String username;

    // 描述
    private String description;

    // 方法名
    private String method;

    // 参数
    @Column(columnDefinition = "text")  //@Column注释定义了将成员属性映射到关系表中的哪一列和该列的结构信息
    private String params;

    // 日志类型
    @Column(name = "log_type")
    private String logType;

    // 请求ip
    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "address")
    private String address;

    private String browser;

    // 请求耗时
    private Long time;

    // 异常详细
    @Column(name = "exception_detail", columnDefinition = "text")
    private byte[] exceptionDetail;

    // 创建日期
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}

package me.zhengjie.utils;

public class Constants {
    public static String IMPORTPKG = "import org.apache.flink.api.common.functions.FlatMapFunction;\n" +
            "import org.apache.flink.api.common.serialization.SimpleStringSchema;\n" +
            "import org.apache.flink.streaming.api.datastream.DataStream;\n" +
            "import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;\n" +
            "import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;\n" +
            "import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;\n" +
            "import org.apache.flink.api.java.tuple.*;\n" +
            "import org.apache.flink.api.common.typeinfo.Types;\n" +
            "import org.apache.flink.utils.Collector;\n" +
            "import java.utils.LinkedList;\n" +
            "import java.utils.Properties;\n" +
            "import org.apache.flink.api.common.functions.MapFunction;\n" +
            "import org.apache.flink.api.java.tuple.Tuple2;\n" +
            "import org.apache.flink.api.java.tuple.Tuple3;\n" +
            "import org.apache.flink.api.java.tuple.Tuple4;\n"   +
            "import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;\n" +
            "import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;\n" +
            "import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;\n" +
            "import org.apache.flink.streaming.api.watermark.Watermark;\n" +
            "import org.apache.flink.streaming.api.windowing.time.Time;\n" +
            "import javax.annotation.Nullable;\n" +
            "import java.text.SimpleDateFormat;\n" +
            "import java.utils.*;\n" +
            "import org.apache.flink.api.common.functions.FilterFunction;\n" +
            "import org.apache.flink.streaming.api.datastream.SplitStream;\n" +
            "import org.apache.flink.streaming.api.collector.selector.OutputSelector;\n" +
            "import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;\n" +
            "import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;\n" +
            "import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;\n" +
            "import org.apache.flink.streaming.api.windowing.assigners.*;\n" +
            "import org.apache.flink.api.common.functions.CoGroupFunction;\n" +
            "import org.apache.flink.api.common.functions.*;\n" +
            "import org.apache.flink.streaming.api.windowing.windows.TimeWindow;\n" +
            "import org.apache.flink.api.java.functions.KeySelector;\n" +
            "import java.text.ParseException;\n" +
            "import org.apache.flink.streaming.api.TimeCharacteristic;\n";
    public static String MAINSTART = "public class DataShow {\n" +
            "           public static HashMap<String, Long> labelMap = new HashMap<>();\n" +
            "           public static Long labelIndex = 0l;\n" +
            "       public static void main(String[] args) throws Exception {\n" +
            "            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();\n" +
            "            env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);\n";

    public static String MAINEND = " }\n" +
            "}";

    public static String DATASOURCE = "";
    // public static String CREATEENV = "StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();";
//    public static Map<String, String> ClassMap = {"a":"b"};

}

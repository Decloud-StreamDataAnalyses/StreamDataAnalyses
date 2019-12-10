package me.zhengjie.utils.compiler_post_jar;

import java.io.*;

import static me.zhengjie.utils.compiler_post_jar.PostRunJar.PostJar;
import static me.zhengjie.utils.compiler_post_jar.PostRunJar.RunJar;

public class UploadRun {

    public static void UploadRun(String code) throws IOException {

        // 代码生成java文件
        String jarDir;
        String osName = System.getProperties().getProperty("os.name");
        if(osName.contains("Windows"))
            jarDir = "D:\\test2\\GenerateFlink";
        else
            jarDir = "/home/user802/bigdata/GenerateFlink/";
        File file = new File(jarDir+"\\DataShow.java");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(code);
        bw.flush();
        bw.close();
        fw.close();

        //编译+打包
        CompilerAndJarTools cl = new CompilerAndJarTools(jarDir,
                jarDir+"/classes", jarDir+ "/target/DataShow.jar");
        cl.complier();
        cl.generateJar();

        //rest
//        String[] uploadCmds = new String[]{"curl", "-X", "POST", "-H", "\"Expect:\"", "-F"};
//        String jarfile = "\"jarfile=@" + jarDir+ "/target/DataShow.jar" + "\"";
//        String cluster ="10.5.83.175:8081/jars/upload";
//        List<String> uploadJar = new ArrayList<String>();
//        for (int i = 0; i < uploadCmds.length ; i++) {
//            uploadJar.add(uploadCmds[i]);
//        }
//        uploadJar.add(jarfile);
//        uploadJar.add(cluster);
//        String jarId = execCurlUpload(uploadJar);

//        String[] runCmds = new String[]{"curl","-X","POST"};
//        List<String> runJar = new ArrayList<String>();
//        for (int i = 0; i < runCmds.length; i++) {
//            runJar.add(runCmds[i]);
//        }
//        runJar.add("http://"+"10.5.83.175"+":8081"+"/jars/"+jarId+"/run");
//        System.out.println(runJar);
//        execCurlRun(runJar);

        String jarFilePath;
        if(osName.contains("Windows"))
            jarFilePath = jarDir+ "/target/DataShow.jar";
        else
            jarFilePath = "//home//user802//bigdata//GenerateFlink//target//DataShow.jar";
        File jarFile = new File(jarFilePath);

        // 提交 jar
        String jarId = PostJar(jarFile);

        // 运行 jar
        RunJar(jarId);

    }

}

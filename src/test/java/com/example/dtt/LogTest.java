package com.example.dtt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LogTest {
    //@Test
    public void printLog() throws IOException {
        //假设我们已经通过其他的方式拿到了applicationId 一般都是通过livy 提供的rest api接口获取
        String applicationId = "application_1587284642166_0001";

        /**
         * 根据applicationId 获取containerId 和 nodeId 其中appOwner可能是租户，
         * 也可能是代理用户，根据业务自行设置，本地测试的时候我并没有创建hadoop user,
         * 所以直接给root
         */
        Map<String, String> map = HadoopLogUtils.getContaines(applicationId, "root");
       /* map.forEach((k, v) ->{
            System.out.println("containeId is: " + k + "nodeId is: " + v);
        });*/

        /**
         * 根据 containeId 和 nodeId 拿到日志 输出到控制台或者写入文件
         */

        AtomicInteger count = new AtomicInteger(0);
        map.forEach((containeId, nodeId) -> {
            OutputStream in = null;
            try {
                File file = new File("E:\\openSource\\hadoop_learning\\target\\" + containeId + "_" + count.get() + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                in = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //如果不写入文件 直接使用 PrintStream printStream = System.out
            PrintStream printStream = new PrintStream(in);
            List<String> logType = new ArrayList<>(1);
            //hadoop 2.X 日志类型有 stdout stderr 3.X 有stdout gc.current
            logType.add("stderr");
            try {
                HadoopLogUtils.dumpAContainersLogs(applicationId,
                        containeId,
                        nodeId,
                        "root", printStream ,logType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            count.getAndIncrement();
        });

    }

}

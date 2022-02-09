package com.example.dtt.utils;

import com.example.dtt.constant.load.LoadFileConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

/**
 * @author reid
 * @date 2022/1/10 16:07
 * @describe hadoop 环境信息工具类
 */
public class HadoopUtils {
    private static final Logger logger = LoggerFactory.getLogger(HadoopUtils.class);

    /**
     * 获取hdfs文件管理
     *
     * @return
     * @throws IOException
     */
    public static FileSystem getDevFS() {
        FileSystem fs = null;
        Configuration config = new Configuration();
        config.set("fs.defaultFS", LoadFileConstants.HDFS_NAME_DEV);
        config.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        try {
            fs = FileSystem.get(config);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("创建hdfs 文件操作对象失败");
        }
        return fs;
    }

    /**
     * HA模式
     * @return
     */
    public static FileSystem getProdFS() {
        FileSystem fs = null;
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS",LoadFileConstants.HDFS_NAME_PROD);
        conf.set("dfs.nameservices", "nameservice1");
        conf.set("dfs.ha.namenodes.nameservice1", "namenode246,namenode265");
        conf.set("dfs.namenode.rpc-address.nameservice1.namenode246","s1-prd-base-hadoop-01:8022");
        conf.set("dfs.namenode.rpc-address.nameservice1.namenode265","s1-prd-base-hadoop-02:8020");
        conf.set("dfs.client.failover.proxy.provider.nameservice1","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        try {
             fs = FileSystem.get(URI.create(LoadFileConstants.HDFS_NAME_PROD), conf, "hdfs");
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("创建hdfs 文件操作对象失败");
        }
        return fs;
    }
}

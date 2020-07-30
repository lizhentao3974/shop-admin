package com.fh.shop.admin.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public class OssUtil {
    private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";

    private static final String ACCESSKEYID = "LTAI4GAAge1681NzJBFQYVBX";

    private static final String ACCESSKEYSECRET = "OWwW6kV0zCj3k4IMDTBcqIR3nipZlB";

    private static final String BUCKETNAME = "fhdmd";

    private static final String URL = "https://fhdmd.oss-cn-beijing.aliyuncs.com/";


    private static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);
        return suffix;
    }


    //上传文件
    public static String upload(InputStream inputStream, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);

        // 上传文件流。
        String uploadFileName = UUID.randomUUID().toString() + getSuffix(fileName);

        String pathName = DateUtil.date2str(new Date(), DateUtil.Y_M_D);

        String objectName = pathName + "/" + uploadFileName;
        try {
            ossClient.putObject(BUCKETNAME, objectName, inputStream);
            return URL + objectName;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }


    //删除文件
    public static void delete(String imagePath) {

        String objectName = imagePath.replace(URL, "");
        // <yourObjectName>表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);

        // 删除文件。
        ossClient.deleteObject(BUCKETNAME, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}

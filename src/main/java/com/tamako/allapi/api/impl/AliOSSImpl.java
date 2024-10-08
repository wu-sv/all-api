/**
 * 所有的api接口的实现
 */
package com.tamako.allapi.api.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.tamako.allapi.api.AliOSSApi;
import com.tamako.allapi.configuration.AliProperties;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 阿里云OSS接口实现
 *
 * @author Tamako
 * @since 2024/8/26 14:14
 */
public class AliOSSImpl implements AliOSSApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();

    /**
     * 阿里云OSS配置
     */

    private final AliProperties aliProperties;

    public AliOSSImpl(AliProperties aliProperties) {
        this.aliProperties = aliProperties;
    }

    /**
     * 简单上传
     *
     * @param file            上传文件流
     * @param fileName        上传文件名
     * @param forbidOverwrite 是否禁止覆盖(false:不禁止,true:禁止)
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 上传成功后的url
     */
    @Override
    public String upload(@NotNull InputStream file, @NotNull String fileName, @NotNull Boolean forbidOverwrite, @NotNull Boolean readPermissions, Date expiration) {
        OSS client = this.initClient();
        try {
            return this.upload(client, file, fileName, forbidOverwrite, readPermissions, expiration);
        } catch (OSSException | ClientException oe) {
            log.error("上传失败", oe);
            throw new RuntimeException(oe);
        } finally {
            this.closeClient(client);
        }
    }

    @Override
    public String upload(@NotNull OSS client, @NotNull InputStream file, @NotNull String fileName, @NotNull Boolean forbidOverwrite, @NotNull Boolean readPermissions, Date expiration) throws OSSException, ClientException {
        fileName = this.formatCheckAndConvert(fileName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(aliProperties.getOss().getBucketName(), fileName, file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader("x-oss_forbid_overwrite", forbidOverwrite.toString());
        putObjectRequest.setMetadata(metadata);
        client.putObject(putObjectRequest);
        String url;
        if (!readPermissions) {
            //公共读
            url = UrlBuilder.of()
                    .setScheme("https")
                    .setHost(aliProperties.getOss().getBucketName() + "." + aliProperties.getOss().getEndpoint().replaceAll("https://", ""))
                    .addPath(fileName)
                    .build();
        } else {
            //私有读
            url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration).toString();
        }
        return this.decode(url);
    }

    /**
     * 简单上传(默认不禁止覆盖且为公共读)
     *
     * @param file     上传文件流
     * @param fileName 上传文件名
     */
    @Override
    public String upload(@NotNull InputStream file, @NotNull String fileName) {
        return this.upload(file, fileName, false, false, null);
    }

    /**
     * 生成以GET方法访问的签名URL
     *
     * @param fileName   文件名
     * @param expiration 过期时间
     * @return 签名URL
     */
    @Override
    public String generatePresignedUrl(@NotNull String fileName, @NotNull Date expiration) {
        fileName = this.formatCheckAndConvert(fileName);
        OSS client = this.initClient();
        try {
            boolean exists = this.exists(client, fileName);
            if (!exists) {
                throw new RuntimeException("文件不存在");
            }
            URL url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration);
            return this.decode(url.toString());
        } catch (OSSException | ClientException oe) {
            log.error("生成签名URL失败", oe);
            throw new RuntimeException(oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 生成以GET方法访问的签名URLs
     *
     * @param fileNames  文件名
     * @param expiration 过期时间
     * @return 签名URLs
     */
    @Override
    public List<String> generatePresignedUrl(@NotNull List<String> fileNames, @NotNull Date expiration) {
        this.formatCheckAndConvert(fileNames);
        OSS client = this.initClient();
        try {
            List<String> urls = new ArrayList<>();
            fileNames.forEach(fileName -> {
                boolean exists = this.exists(client, fileName);
                if (!exists) {
                    throw new RuntimeException("文件不存在");
                }
                String url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration).toString();
                urls.add(this.decode(url));
            });
            return urls;
        } catch (OSSException | ClientException oe) {
            log.error("生成签名URL失败", oe);
            throw new RuntimeException(oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 删除文件或目录（如果要删除目录，目录必须为空）
     *
     * @param fileName 文件名
     */
    @Override
    public void delete(@NotNull String fileName) {
        fileName = this.formatCheckAndConvert(fileName);
        OSS client = this.initClient();
        try {
            if (!this.exists(client, fileName)) {
                throw new RuntimeException("文件不存在");
            }
            client.deleteObject(aliProperties.getOss().getBucketName(), fileName);
        } catch (OSSException | ClientException oe) {

            log.error("删除文件失败", oe);
            throw new RuntimeException(oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 批量删除文件或目录（如果要删除目录，目录必须为空）
     *
     * @param fileNameList 文件名列表
     */
    @Override
    public void delete(List<String> fileNameList) {
        this.formatCheckAndConvert(fileNameList);
        OSS client = this.initClient();
        try {
            client.deleteObjects(new DeleteObjectsRequest(aliProperties.getOss().getBucketName()).withKeys(fileNameList).withEncodingType("url"));
        } catch (OSSException | ClientException oe) {
            log.error("批量删除文件失败", oe);
            throw new RuntimeException(oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 判断文件是否存在(一般配合delete方法使用)
     *
     * @param client   OSS客户端
     * @param fileName 文件名
     * @return 是否存在
     */
    private boolean exists(@NotNull OSS client, @NotNull String fileName) throws OSSException, ClientException {
        return client.doesObjectExist(aliProperties.getOss().getBucketName(), fileName);
    }

    /**
     * 文件格式校验与转换
     *
     * @param fileName 文件名
     * @return 格式化后的文件名
     */
    private String formatCheckAndConvert(String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            throw new RuntimeException("文件名错误");
        }
        //去掉开头的“/”
        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        return fileName;
    }

    /**
     * 批量文件格式校验与转换
     *
     * @param fileNameList 文件名列表
     */
    private void formatCheckAndConvert(List<String> fileNameList) {
        fileNameList.replaceAll(this::formatCheckAndConvert);
    }

    /**
     * 初始化OSS客户端
     *
     * @return OSS客户端
     */
    private OSS initClient() {
        return new OSSClientBuilder().build(aliProperties.getOss().getEndpoint(), aliProperties.getAccessKeyId(), aliProperties.getAccessKeySecret());
    }

    /**
     * 关闭OSS客户端
     *
     * @param ossClient OSS客户端
     */
    private void closeClient(OSS ossClient) {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    /**
     * 解码URL(将unicode编码转为中文)
     *
     * @param url URL
     * @return 解码后的URL
     */
    private String decode(String url) {
        return URLUtil.decode(url).replaceAll("\\+", "%2B");
    }
}

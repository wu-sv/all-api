package com.tamako.allapi.api;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.tamako.allapi.wechat.model.AliProperties;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author Tamako
 * @since 2024/8/26 14:14
 */
public class AliOSSApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();

    /**
     * 阿里云OSS配置
     */
    @Resource
    private AliProperties aliProperties;

    /**
     * 简单上传
     *
     * @param file            上传文件流
     * @param fileName        上传文件名
     * @param forbidOverwrite 是否禁止覆盖(false:不禁止,true:禁止)
     * @param readPermissions 读权限(true:公共读,false:私有读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 上传成功后的url
     */
    public String upload(@NotNull InputStream file, @NotNull String fileName, @NotNull Boolean forbidOverwrite, @NotNull Boolean readPermissions, Date expiration) {
        OSS client = this.initClient();
        try {
            if (StrUtil.isEmpty(fileName)) {
                throw new RuntimeException("文件名错误");
            }
            //去掉开头的“/”
            if (fileName.startsWith("/")) {
                fileName = fileName.substring(1);
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliProperties.getOss().getBucketName(), fileName, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader("x-oss_forbid_overwrite", forbidOverwrite.toString());
            putObjectRequest.setMetadata(metadata);
            client.putObject(putObjectRequest);

            if (readPermissions) {
                //公共读
                String url = UrlBuilder.of()
                        .setScheme("https")
                        .setHost(aliProperties.getOss().getBucketName() + "." + aliProperties.getOss().getEndpoint())
                        .addPath(fileName)
                        .build();
                return URLUtil.decode(url);
            } else {
                //私有读
                URL url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration);
                return URLUtil.decode(url.toExternalForm());
            }
        } catch (OSSException | ClientException oe) {
            log.error("上传失败", oe);
            throw new RuntimeException(oe);
        } finally {
            client.shutdown();
        }
    }

    /**
     * 简单上传(默认不禁止覆盖且为公共读)
     *
     * @param file     上传文件流
     * @param fileName 上传文件名
     */
    public String upload(@NotNull InputStream file, @NotNull String fileName) {
        return this.upload(file, fileName, false, true,null);
    }

    /**
     * 生成以GET方法访问的签名URL
     *
     * @param fileName   文件名
     * @param expiration 过期时间
     * @return 签名URL
     */
    public String generatePresignedUrl(@NotNull String fileName, @NotNull Date expiration) {
        OSS client = this.initClient();
        try {
            URL url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration);
            return url.toExternalForm();
        } catch (OSSException | ClientException oe) {
            log.error("生成签名URL失败", oe);
            throw new RuntimeException(oe);
        } finally {
            client.shutdown();
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
     * 初始化OSS客户端
     *
     * @return OSS客户端
     */
    private OSS initClient() {
        return new OSSClientBuilder().build(aliProperties.getOss().getEndpoint(), aliProperties.getAccessKeyId(), aliProperties.getAccessKeySecret());
    }
}

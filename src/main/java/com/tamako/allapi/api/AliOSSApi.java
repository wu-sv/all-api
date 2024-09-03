
package com.tamako.allapi.api;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 阿里云对象存储服务API接口
 *
 * @author Tamako
 * @since 2024/8/28 13:11
 */
public interface AliOSSApi {
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
    String upload(@NotNull InputStream file, @NotNull String fileName, @NotNull Boolean forbidOverwrite, @NotNull Boolean readPermissions, Date expiration);

    /**
     * 简单上传(不做OssClient的关闭和开启操作)
     *
     * @param client          OssClient对象
     * @param file            上传文件流
     * @param fileName        上传文件名
     * @param forbidOverwrite 是否禁止覆盖(false:不禁止,true:禁止)
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 上传成功后的url
     */
    String upload(@NotNull OSS client, @NotNull InputStream file, @NotNull String fileName, @NotNull Boolean forbidOverwrite, @NotNull Boolean readPermissions, Date expiration) throws OSSException, ClientException;


    /**
     * 简单上传(默认不禁止覆盖且为公共读)
     *
     * @param file     上传文件流
     * @param fileName 上传文件名
     */
    String upload(@NotNull InputStream file, @NotNull String fileName);

    /**
     * 生成以GET方法访问的签名URL
     *
     * @param fileName   文件名
     * @param expiration 过期时间
     * @return 签名URL
     */
    String generatePresignedUrl(@NotNull String fileName, @NotNull Date expiration);

    /**
     * 生成以GET方法访问的签名URLs
     *
     * @param fileNames  文件名
     * @param expiration 过期时间
     * @return 签名URLs
     */
    List<String> generatePresignedUrl(@NotNull List<String> fileNames, @NotNull Date expiration);

    /**
     * 删除文件或目录（如果要删除目录，目录必须为空）
     *
     * @param fileName 文件名
     */
    void delete(@NotNull String fileName);

    /**
     * 批量删除文件或目录（如果要删除目录，目录必须为空）
     *
     * @param fileNameList 文件名列表
     */
    void delete(List<String> fileNameList);
}

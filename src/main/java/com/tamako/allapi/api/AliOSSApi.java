
package com.tamako.allapi.api;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PartETag;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * @return 上传成功后的url
     */
    String upload(@NotNull InputStream file, @NotNull String fileName);

    /**
     * 分片上传初始化
     *
     * @param fileName 文件名
     * @return 初始化成功后的uploadId
     */
    String initiateMultipartUpload(@NotNull String fileName);

    /**
     * 分片上传初始化(带OSS客户端参数，需要手动关闭)
     *
     * @param fileName 文件名
     * @return 初始化成功后的uploadId和OSS客户端
     */
    Map<String, Object> initiateMultipartUploadAndOss(@NotNull String fileName);

    /**
     * 分片上传
     *
     * @param partFile   上传文件流
     * @param fileName   上传文件名
     * @param partSize   分片大小(单位：Byte)
     * @param partNumber 分片序号（从1开始）
     * @param uploadId   分片上传ID
     * @return 上传成功后的url
     */
    PartETag uploadPart(@NotNull InputStream partFile, @NotNull String fileName,
                        @NotNull Long partSize, @NotNull Integer partNumber,
                        @NotNull String uploadId);

    /**
     * 分片上传(带OSS客户端参数，需要手动关闭)
     *
     * @param partFile   上传文件流
     * @param fileName   上传文件名
     * @param partSize   分片大小(单位：Byte)
     * @param partNumber 分片序号（从1开始）
     * @param uploadId   分片上传ID
     * @param client     OSS客户端
     * @return 上传成功后的url
     */
    PartETag uploadPart(@NotNull InputStream partFile, @NotNull String fileName,
                        @NotNull Long partSize, @NotNull Integer partNumber,
                        @NotNull String uploadId, @NotNull OSS client);

    /**
     * 合并分片上传
     *
     * @param fileName        文件名
     * @param uploadId        分片上传ID
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 合并成功后的url
     */
    String completeMultipartUpload(@NotNull String fileName, @NotNull String uploadId, @NotNull Boolean readPermissions, @NotNull Date expiration);

    /**
     * 合并分片上传(带OSS客户端参数，在此处关闭)
     *
     * @param fileName        文件名
     * @param uploadId        分片上传ID
     * @param client          OSS客户端
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 合并成功后的url
     */
    String completeMultipartUpload(@NotNull String fileName, @NotNull String uploadId, @NotNull Boolean readPermissions, @NotNull Date expiration, @NotNull OSS client);

    /**
     * 取消分片上传
     *
     * @param fileName 文件名
     * @param uploadId 分片上传ID
     */
    void abortMultipartUpload(@NotNull String fileName, @NotNull String uploadId);

    /**
     * 生成以GET方法访问的签名URL
     *
     * @param fileName   文件名
     * @param expiration 过期时间
     * @return 签名URL
     */
    String generatePresignedUrl(@NotNull String fileName, @NotNull Date expiration);

    /**
     * 生成以GET方法访问的签名URL
     *
     * @param client           OSS客户端
     * @param fileName         文件名
     * @param notExistFileName 不存在的文件名(替换)
     * @param expiration       过期时间
     * @param checkExists      是否检查文件是否存在
     * @return 签名URL
     */
    String generatePresignedUrl(@NotNull OSS client, @NotNull String fileName, String notExistFileName, @NotNull Date expiration, @NotNull Boolean checkExists);

    /**
     * 生成以GET方法访问的签名URL
     *
     * @param fileName         文件名
     * @param notExistFileName 不存在的文件名(替换)
     * @param expiration       过期时间
     * @param checkExists      是否检查文件是否存在
     * @return 签名URL
     */
    String generatePresignedUrl(@NotNull String fileName, String notExistFileName, @NotNull Date expiration, @NotNull Boolean checkExists);

    /**
     * 生成以GET方法访问的签名URLs
     *
     * @param fileNames  文件名
     * @param expiration 过期时间
     * @return 签名URLs
     */
    List<String> generatePresignedUrl(@NotNull List<String> fileNames, @NotNull Date expiration);

    /**
     * 生成以GET方法访问的签名URLs
     *
     * @param fileNames   文件名
     * @param expiration  过期时间
     * @param checkExists 是否检查文件是否存在
     * @return 签名URLs
     */
    List<String> generatePresignedUrl(@NotNull List<String> fileNames, @NotNull Date expiration, @NotNull Boolean checkExists);

    /**
     * 生成以GET方法访问的签名URLs
     *
     * @param fileNames        文件名
     * @param notExistFileName 不存在的文件名(替换)
     * @param expiration       过期时间
     * @param checkExists      是否检查文件是否存在
     * @return 签名URLs
     */
    List<String> generatePresignedUrl(@NotNull List<String> fileNames, String notExistFileName, @NotNull Date expiration, @NotNull Boolean checkExists);

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

    /**
     * 流式下载文件
     *
     * @param fileName 文件名
     * @return 文件字节数组
     */
    byte[] download2Bytes(String fileName);

    /**
     * 下载到本地文件
     *
     * @param fileName 文件名
     * @param file     本地文件
     */
    void download2File(String fileName, File file);
}

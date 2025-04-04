/**
 * 所有的api接口的实现
 */
package com.tamako.allapi.api.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.*;
import com.tamako.allapi.api.AliOSSApi;
import com.tamako.allapi.api.impl.base.AliBaseImpl;
import com.tamako.allapi.configuration.properties.AliProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import com.tamako.allapi.utils.URLUtil;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * 阿里云OSS接口实现
 *
 * @author Tamako
 * @since 2024/8/26 14:14
 */
public class AliOSSImpl extends AliBaseImpl implements AliOSSApi {

    /**
     * 上传ID常量
     */
    public static final String UPLOAD_ID = "uploadId";

    /**
     * OSS客户端常量
     */
    public static final String OSS_CLIENT = "ossClient";



    /**
     * 构造方法
     *
     * @param aliProperties 阿里云OSS配置
     */
    public AliOSSImpl(AliProperties aliProperties) {
        super(aliProperties);
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
            throw new AllApiException(PlatformEnum.ALI, "上传失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 简单上传(带OSS客户端参数，需要手动关闭)
     *
     * @param client          OssClient对象
     * @param file            上传文件流
     * @param fileName        上传文件名
     * @param forbidOverwrite 是否禁止覆盖(false:不禁止,true:禁止)
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 上传成功后的url
     * @throws OSSException    OSS异常
     * @throws ClientException 客户端异常
     */
    @Override
    public String upload(@NotNull OSS client, @NotNull InputStream file, @NotNull String fileName, @NotNull Boolean forbidOverwrite, @NotNull Boolean readPermissions, Date expiration) throws OSSException, ClientException {
        fileName = this.formatCheckAndConvert(fileName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(aliProperties.getOss().getBucketName(), fileName, file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader("x-oss_forbid_overwrite", forbidOverwrite.toString());
        putObjectRequest.setMetadata(metadata);
        client.putObject(putObjectRequest);
        return getUrl(client, fileName, readPermissions, expiration);
    }

    /**
     * 格式检查并转换文件名
     *
     * @param client          OSS客户端
     * @param fileName        文件名
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 格式化后的文件名
     */
    private @NotNull String getUrl(@NotNull OSS client, @NotNull String fileName, @NotNull Boolean readPermissions, Date expiration) {
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
     * 分片上传初始化
     *
     * @param fileName 文件名
     * @return 初始化成功后的uploadId
     */
    @Override
    public String initiateMultipartUpload(@NotNull String fileName) {
        String bucketName = aliProperties.getOss().getBucketName();
        OSS client = this.initClient();
        try {
            InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, fileName);
            ObjectMetadata metadata = new ObjectMetadata();
            request.setObjectMetadata(metadata);
            InitiateMultipartUploadResult upResult = client.initiateMultipartUpload(request);
            return upResult.getUploadId();
        } catch (OSSException | ClientException oe) {
            log.error("分片上传初始化失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "分片上传初始化失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 分片上传初始化(带OSS客户端参数，需要手动关闭)
     *
     * @param fileName 文件名
     * @return 初始化成功后的uploadId和OSS客户端
     */
    @Override
    public Map<String, Object> initiateMultipartUploadAndOss(@NotNull String fileName) {
        String bucketName = aliProperties.getOss().getBucketName();
        OSS client = this.initClient();
        Map<String, Object> result = new HashMap<>();
        result.put(OSS_CLIENT, client);
        try {
            InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, fileName);
            ObjectMetadata metadata = new ObjectMetadata();
            request.setObjectMetadata(metadata);
            InitiateMultipartUploadResult upResult = client.initiateMultipartUpload(request);
            result.put(UPLOAD_ID, upResult.getUploadId());
            return result;
        } catch (OSSException | ClientException oe) {
            log.error("分片上传初始化失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "分片上传初始化失败", oe);
        }
    }

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
    @Override
    public PartETag uploadPart(@NotNull InputStream partFile, @NotNull String fileName,
                               @NotNull Long partSize, @NotNull Integer partNumber,
                               @NotNull String uploadId) {
        OSS client = this.initClient();
        try {
            return uploadPart(partFile, fileName, partSize, partNumber, uploadId, client);
        } catch (OSSException | ClientException oe) {
            log.error("分片上传失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "分片上传失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

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
    @Override
    public PartETag uploadPart(@NotNull InputStream partFile, @NotNull String fileName,
                               @NotNull Long partSize, @NotNull Integer partNumber,
                               @NotNull String uploadId, @NotNull OSS client) {
        try {
            String bucketName = aliProperties.getOss().getBucketName();
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(fileName);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(partFile);
            uploadPartRequest.setPartSize(partSize);
            uploadPartRequest.setPartNumber(partNumber);
            UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
            return uploadPartResult.getPartETag();
        } catch (OSSException | ClientException oe) {
            log.error("分片上传失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "分片上传失败", oe);
        }
    }

    /**
     * 合并分片上传
     *
     * @param fileName        文件名
     * @param uploadId        分片上传ID
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @return 合并成功后的url
     */
    @Override
    public String completeMultipartUpload(@NotNull String fileName, @NotNull String uploadId,
                                          @NotNull Boolean readPermissions, @NotNull Date expiration) {
        OSS client = this.initClient();
        return this.completeMultipartUpload(fileName, uploadId, readPermissions, expiration, client);
    }

    /**
     * 合并分片上传(带OSS客户端参数，在此处关闭)
     *
     * @param fileName        文件名
     * @param uploadId        分片上传ID
     * @param readPermissions 读权限(true:私有读,false:公共读)
     * @param expiration      过期时间(时间要在当前时间之后)
     * @param client          OSS客户端
     * @return 合并成功后的url
     */
    @Override
    public String completeMultipartUpload(@NotNull String fileName, @NotNull String uploadId,
                                          @NotNull Boolean readPermissions, @NotNull Date expiration, @NotNull OSS client) {
        try {
            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    new CompleteMultipartUploadRequest(aliProperties.getOss().getBucketName(), fileName, uploadId, null);
            Map<String, String> headers = new HashMap<>();
            headers.put("x-oss-complete-all", "yes");
            completeMultipartUploadRequest.setHeaders(headers);
            client.completeMultipartUpload(completeMultipartUploadRequest);
            return this.getUrl(client, fileName, readPermissions, expiration);
        } catch (OSSException | ClientException oe) {
            log.error("合并分片上传失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "合并分片上传失败", oe);
        } finally {
            this.closeClient(client);
        }
    }


    /**
     * 取消分片上传
     *
     * @param fileName 文件名
     * @param uploadId 分片上传ID
     */
    @Override
    public void abortMultipartUpload(@NotNull String fileName, @NotNull String uploadId) {
        OSS client = this.initClient();
        try {
            AbortMultipartUploadRequest abortMultipartUploadRequest =
                    new AbortMultipartUploadRequest(aliProperties.getOss().getBucketName(), fileName, uploadId);
            client.abortMultipartUpload(abortMultipartUploadRequest);
        } catch (OSSException | ClientException oe) {
            log.error("取消分片上传失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "取消分片上传失败", oe);
        } finally {
            this.closeClient(client);
        }
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
                throw new AllApiException("文件不存在");
            }
            URL url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration);
            return this.decode(url.toString());
        } catch (OSSException | ClientException oe) {
            log.error("生成签名URL失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "生成签名URL失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

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
    @Override
    public String generatePresignedUrl(@NotNull OSS client, @NotNull String fileName, String notExistFileName, @NotNull Date expiration, @NotNull Boolean checkExists) {
        fileName = this.formatCheckAndConvert(fileName);
        if (checkExists) {
            boolean exists = this.exists(client, fileName);
            if (!exists) {
                if (StrUtil.isBlank(notExistFileName)) {
                    return "文件不存在";
                } else {
                    return notExistFileName;
                }
            } else {
                String url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration).toString();
                return this.decode(url);
            }
        } else {
            String url = client.generatePresignedUrl(aliProperties.getOss().getBucketName(), fileName, expiration).toString();
            return this.decode(url);
        }
    }

    /**
     * 生成以GET方法访问的签名URL
     *
     * @param fileName         文件名
     * @param notExistFileName 不存在的文件名(替换)
     * @param expiration       过期时间
     * @param checkExists      是否检查文件是否存在
     * @return 签名URL
     */
    @Override
    public String generatePresignedUrl(@NotNull String fileName, String notExistFileName, @NotNull Date expiration, @NotNull Boolean checkExists) {
        OSS client = this.initClient();
        return this.generatePresignedUrl(client, fileName, notExistFileName, expiration, checkExists);
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
        return this.generatePresignedUrl(fileNames, null, expiration, true);
    }

    /**
     * 生成以GET方法访问的签名URLs
     *
     * @param fileNames   文件名
     * @param expiration  过期时间
     * @param checkExists 是否检查文件是否存在
     * @return 签名URLs
     */
    @Override
    public List<String> generatePresignedUrl(@NotNull List<String> fileNames, @NotNull Date expiration, @NotNull Boolean checkExists) {
        return this.generatePresignedUrl(fileNames, null, expiration, checkExists);
    }

    /**
     * 生成以GET方法访问的签名URLs
     *
     * @param fileNames        文件名
     * @param notExistFileName 不存在的文件名(替换)
     * @param expiration       过期时间
     * @param checkExists      是否检查文件是否存在
     * @return 签名URLs
     */
    @Override
    public List<String> generatePresignedUrl(@NotNull List<String> fileNames, String notExistFileName, @NotNull Date expiration, @NotNull Boolean checkExists) {
        OSS client = this.initClient();
        try {
            List<String> urls = new ArrayList<>();
            fileNames.forEach(fileName -> {
                urls.add(this.generatePresignedUrl(client, fileName, notExistFileName, expiration, checkExists));
            });
            return urls;
        } catch (OSSException | ClientException oe) {
            log.error("生成签名URL失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "生成签名URL失败", oe);
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
                throw new AllApiException("文件不存在");
            }
            client.deleteObject(aliProperties.getOss().getBucketName(), fileName);
        } catch (OSSException | ClientException oe) {
            log.error("删除文件失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "删除文件失败", oe);
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
            throw new AllApiException(PlatformEnum.ALI, "批量删除文件失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 流式下载文件
     *
     * @param fileName 文件名
     * @return 文件字节数组
     */
    @Override
    public byte[] download2Bytes(String fileName) {
        fileName = formatCheckAndConvert(fileName);
        OSS client = this.initClient();
        try {
            OSSObject ossObject = client.getObject(aliProperties.getOss().getBucketName(), fileName);
            InputStream inputStream = ossObject.getObjectContent();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 读取文件内容到字节数组。
            byte[] readBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(readBuffer)) != -1) {
                byteArrayOutputStream.write(readBuffer, 0, bytesRead);
            }
            // 获取最终的字节数组。
            return byteArrayOutputStream.toByteArray();
        } catch (OSSException | ClientException | IOException oe) {
            log.error("流式下载文件失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "流式下载文件失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 下载到本地文件
     *
     * @param fileName 文件名
     * @param file     本地文件
     */
    @Override
    public void download2File(String fileName, File file) {
        fileName = formatCheckAndConvert(fileName);
        OSS client = this.initClient();
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(aliProperties.getOss().getBucketName(), fileName);
            client.getObject(getObjectRequest, file);
        } catch (OSSException | ClientException oe) {
            log.error("下载到本地文件失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "下载到本地文件失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName 文件名
     * @return true:存在,false:不存在
     */
    @Override
    public Boolean exist(String fileName) {
        OSS client = this.initClient();
        try {
            return this.exists(client, fileName);
        } catch (OSSException | ClientException oe) {
            log.error("判断文件是否存在失败", oe);
            throw new AllApiException(PlatformEnum.ALI, "判断文件是否存在失败", oe);
        } finally {
            this.closeClient(client);
        }
    }

    /**
     * 初始化OSS客户端
     *
     * @return OSS客户端
     */
    @Override
    public OSS initClient() {
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(aliProperties.getAccessKeyId(), aliProperties.getAccessKeySecret());
        String region = aliProperties.getOss().getRegion();
        region = StrUtil.isEmpty(region) ? URLUtil.getAliOssRegion(aliProperties.getOss().getEndpoint()) : region;
        return OSSClientBuilder.create()
                .endpoint(aliProperties.getOss().getEndpoint())
                .credentialsProvider(credentialProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
    }

    /**
     * 关闭OSS客户端
     *
     * @param ossClient OSS客户端
     */
    @Override
    public void closeClient(OSS ossClient) {
        if (ossClient != null) {
            ossClient.shutdown();
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
            throw new AllApiException("文件名错误");
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
     * 解码URL(将unicode编码转为中文)
     *
     * @param url URL
     * @return 解码后的URL
     */
    private String decode(String url) {
        return URLUtil.decode(url).replaceAll("\\+", "%2B");
    }
}

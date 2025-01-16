package com.tamako.allapi.api;


import java.util.List;

/**
 * 阿里云函数计算API
 *
 * @author Tamako
 * @since 2024/11/12 14:50
 */
public interface AliFCApi {
    /**
     * 压缩oss文件
     *
     * @param fileList oss文件路径列表
     * @return 压缩后的Url
     */
    String zipOssFile(List<String> fileList);
}

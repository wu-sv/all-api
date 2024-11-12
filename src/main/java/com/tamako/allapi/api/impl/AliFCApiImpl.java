package com.tamako.allapi.api.impl;


import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.api.AliFCApi;
import com.tamako.allapi.configuration.AliProperties;
import com.tamako.allapi.utils.NetWorkUtil;

import java.util.List;

/**
 * @author Tamako
 * @since 2024/11/12 14:50
 */
public class AliFCApiImpl implements AliFCApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();

    /**
     * 阿里云OSS配置
     */
    private final AliProperties aliProperties;

    /**
     * 构造方法
     *
     * @param aliProperties 阿里云OSS配置
     */
    public AliFCApiImpl(AliProperties aliProperties) {
        this.aliProperties = aliProperties;
    }

    /**
     * 压缩oss文件
     *
     * @param fileList oss文件路径列表
     * @return 压缩后的Url
     */
    @Override
    public String zipOssFile(List<String> fileList) {
        JSONObject body = new JSONObject();
        body.set("bucket", aliProperties.getOss().getBucketName());
        body.set("source-files", fileList);
        JSONObject jsonObject = NetWorkUtil.postSync(aliProperties.getFc().getZipOssUrl(), body);
        return jsonObject.toBean(String.class);
    }
}

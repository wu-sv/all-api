package com.tamako.allapi.api.impl;


import cn.hutool.json.JSONObject;
import com.tamako.allapi.api.AliFCApi;
import com.tamako.allapi.api.impl.base.AliBaseImpl;
import com.tamako.allapi.configuration.properties.AliProperties;
import com.tamako.allapi.utils.network.DefaultNetWorkUtil;

import java.util.List;

/**
 * @author Tamako
 * @since 2024/11/12 14:50
 */
public class AliFCImpl extends AliBaseImpl implements AliFCApi {
    /**
     * 构造方法
     *
     * @param aliProperties 阿里云OSS配置
     */
    public AliFCImpl(AliProperties aliProperties) {
        super(aliProperties);
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
        JSONObject jsonObject = DefaultNetWorkUtil.postSync(aliProperties.getFc().getZipOssUrl(), body);
        return jsonObject.toBean(String.class);
    }
}

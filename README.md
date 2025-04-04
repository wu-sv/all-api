# ALL-API

### 使用说明：

该项目直接依赖到需要的项目中，然后就可以直接使用了，需要注意pom文件中可能出现版本不匹配的问题，可以自己适当调整版本

所有的实现的接口都在`com.tamako.allapi.api`目录中可以直接查看，目前编写了微信小程序服务器端常用的接口，微信支付（小程序端），阿里云的OSS，短信服务等接口，后续会陆续增加更多的接口。

已提供的接口：

- 微信小程序
- 阿里云OSS
- 阿里云SMS
- 阿里云FC
- 火山引擎RTC

~~在启动类中添加注解以启用~~ 现在增加了spring-auto-configuration，可以不用写注解了

```java

@SpringBootApplication
@EnableAllAPI
public class AllApiTestApplication {
}
```

在这里需要说明微信支付平台证书路径只需要填一个路径加文件名就好，不用管这个文件是否存在，该项目会自动生成该文件。其他文件则需要自行到官网进行获取：

### 相关网页

ApiDoc：[https://apidoc.gitee.com/Tamako520/all-api/](https://apidoc.gitee.com/Tamako520/all-api/)

Gitee：[https://gitee.com/Tamako520/all-api](https://gitee.com/Tamako520/all-api)

GitHub：[https://github.com/wu-sv/all-api](https://github.com/wu-sv/all-api)

### 快速开始

1.在`pom.xml`中导入依赖

```xml

<dependency>
    <groupId>io.gitee.tamako520</groupId>
    <artifactId>all-api</artifactId>
    <version>1.2.7</version>
</dependency>
```

2.在`application.yml`文件中添加配置

该处的配置并不需要全部填写，只需要按需填写即可，也就是说需要哪些功能就填那些参数

```yaml
wechat:
  app-id: <appId>
  secret: <secret>
  pay:
    # 微信支付商户号
    mch-id: <mchId>
    # 微信支付商户密钥
    mch-key: <mchKey>
    # 微信支付回调地址
    notify-url: <notify-url>
    # 证书地址
    cert-path: <apiclient_cert.pem>
    # 证书秘钥地址
    cert-key-path: <apiclient_key.pem>
    # 微信支付平台证书路径
    platform-path: <platform.pem>
ali:
  access-key-id: <accessKeyId>
  access-key-secret: <accessKeySecret>
  oss:
    endpoint: <endpoint>
    bucket-name: <bucketName>
    region: <region>
  sms:
    sign-name: <signName>
    template-code: <templateCode>
  fc:
    # 阿里云函数计算服务名称(oss文件打压缩)
    zipOssUrl: <zipOssUrl>
volc-engine:
  access-key-id: <accessKeyId>
  secret-access-key: <secretAccessKey>
  rtc:
    app-id: <appId>
    app-key: <appKey>
```

~~3.在启动类中添加注解`@EnableAllAPI`~~ 这一步可以省略了

```java
package com.tamako.test;

import com.tamako.allapi.interfaces.EnableAllAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAllAPI
public class AllApiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllApiTestApplication.class, args);
    }

}
```

4.在自己的代码中直接使用

```java
package com.tamako.test.testproject;


import com.tamako.allapi.api.impl.WechatMiniAppImpl;
import com.tamako.allapi.wechat.model.miniapp.vo.GetAccessTokenVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Resource
    private WechatMiniAppImpl wechatMiniAppImpl;


    @GetMapping("/getAccessToken")
    public GetAccessTokenVo getAccessToken() {
        return wechatMiniAppApi.getAccessToken();
    }
}

```

### 联系方式

如有任何问题都可直接发送邮件给我：tamakowusv@qq.com

### 参考文档

[微信支付参考文档](https://pay.weixin.qq.com/docs/merchant/products/mini-program-payment/preparation.html)

[微信支付官网](https://pay.weixin.qq.com)

[微信官方文档-小程序](https://developers.weixin.qq.com/miniprogram/dev/framework/)

[阿里短信服务参考文档](https://help.aliyun.com/zh/sms)

[阿里OSS参考文档](https://help.aliyun.com/zh/oss/)

[火山引擎RTC参考文档](https://www.volcengine.com/docs/6348)

### 后续计划

###### 反则也没人会看到最后

~~1.增加spring-auto-configuration，简化项目引入方式~~（已完成）

2.增加网络请求时的拦截器，并支持自定义配置

3.新增监控和统计功能

4.新增重试机制，提高接口的可用性

5.新增缓存机制

6.新增限流器，提高接口的稳定性

7.增强参数校验，增加接口的健壮性

8.增强对微服务的适配性


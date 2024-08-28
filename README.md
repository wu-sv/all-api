# ALL-API

### 使用说明：

该项目直接依赖到需要的项目中，然后就可以直接使用了，需要注意pom文件中可能出现版本不匹配的问题，可以自己适当调整版本

所有的实现的接口都在`com.tamako.allapi.api`目录中可以直接查看，目前编写了微信小程序服务器端常用的接口和微信支付（小程序端）

在启动类中添加注解以启用

```java
@SpringBootApplication
@EnableAllAPI
public class AllApiTestApplication{}
```

在这里需要说明微信支付平台证书路径只需要填一个路径加文件名就好，不用管这个文件是否存在，该项目会自动生成该文件。其他文件则需要自行到官网进行获取：



### 快速开始

1.在`pom.xml`中导入依赖

```xml
<dependency>
	<groupId>io.gitee.tamako520</groupId>
	<artifactId>all-api</artifactId>
	<version>1.0.1</version>
</dependency>
```

2.在`application.yml`文件中添加配置

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
```

3.在启动类中添加注解`@EnableAllAPI`

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

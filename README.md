# ALL-API

### 使用说明：

该项目直接依赖到需要的项目中，在yml或者properties文件中配置好相关参数，然后就可以直接使用了。

所有的实现的接口都在`com.tamako.allapi.api`目录中可以直接查看，目前编写了微信小程序服务器端常用的接口，微信支付（小程序端），阿里云的OSS，短信服务等接口，后续会陆续增加更多的接口。

已提供的接口：

- 微信小程序
- 阿里云OSS
- 阿里云SMS
- 阿里云FC
- 火山引擎RTC

### 注意事项：

1. 微信支付的文件则需要自行到[微信支付官网](https://pay.weixin.qq.com/)进行获取

2. ~~在启动类中添加注解@EnableAllAPI以启用~~
  现在增加了spring-auto-configuration，可以不用写注解了

3. 在配置文件路径时最好都写绝对路径，否则可能会出现找不到文件的情况

4. 需要什么功能就配置相应的参数就可以，不用全部都配置

5. 适配版本：

   | JDK版本 | SpringBoot版本 |
   | ------- | -------------- |
   | 17+     | 3.x.x          |

   

### 相关网页

ApiDoc：[https://apidoc.gitee.com/Tamako520/all-api/](https://apidoc.gitee.com/Tamako520/all-api/)

Gitee：[https://gitee.com/Tamako520/all-api](https://gitee.com/Tamako520/all-api)

GitHub：[https://github.com/wu-sv/all-api](https://github.com/wu-sv/all-api)

### 快速开始

1. 在`pom.xml`中导入依赖
```xml
<dependency>
    <groupId>io.gitee.tamako520</groupId>
    <artifactId>all-api</artifactId>
    <version>1.3.0</version>
</dependency>
```
2. 在`application.yml`文件中添加配置

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

3. 在自己的代码中直接使用

```java
@RestController
@Slf4j
public class TestController {
    @Resource
    private WechatMiniAppApi wechatMiniAppApi;
    
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


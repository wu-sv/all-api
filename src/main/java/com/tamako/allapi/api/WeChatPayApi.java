package com.tamako.allapi.api;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.BasePayApiEnum;
import com.ijpay.wxpay.enums.v3.OtherApiEnum;
import com.ijpay.wxpay.model.v3.Amount;
import com.ijpay.wxpay.model.v3.Payer;
import com.ijpay.wxpay.model.v3.UnifiedOrderModel;
import com.tamako.allapi.utils.NetWorkUtil;
import com.tamako.allapi.wechat.model.WechatProperties;
import com.tamako.allapi.wechat.model.wxpay.dto.MiniAppPayOrderDto;
import com.tamako.allapi.wechat.model.wxpay.vo.MiniAppPayNotifyVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tamako
 * @since 2021/1/18 16:22
 */

public class WeChatPayApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();
    /**
     * 微信配置
     */
    @Resource
    private WechatProperties wechatProperties;

    /**
     * 小程序支付下单
     *
     * @param dto 订单信息
     * @return 支付结果
     */
    public Map<String, String> miniAppPayOrder(MiniAppPayOrderDto dto) {
        try {
            String timeExpire = DateUtil.format(dto.getTimeExpire(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Amount amount = Amount.builder()
                    .total(dto.getAmountTotal())
                    .build();
            Payer payer = Payer.builder()
                    .openid(dto.getPayerOpenid())
                    .build();
            UnifiedOrderModel model = UnifiedOrderModel.builder()
                    .appid(wechatProperties.getAppId())
                    .mchid(wechatProperties.getPay().getMchId())
                    .description(dto.getDescription())
                    .out_trade_no(dto.getOutTradeNo())
                    .time_expire(timeExpire)
                    .attach(dto.getAttach())
                    .notify_url(wechatProperties.getPay().getNotifyUrl())
                    .amount(amount)
                    .payer(payer)
                    .build();
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.getDomain(),
                    BasePayApiEnum.JS_API_PAY.getUrl(),
                    wechatProperties.getPay().getMchId(),
                    getSerialNumber(),
                    null,
                    wechatProperties.getPay().getCertKeyPath(),
                    JSONUtil.toJsonStr(model)
            );
            //根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wechatProperties.getPay().getPlatformPath());
            log.info("微信支付接口调用结果：{}", verifySignature);
            if (response.getStatus() == HttpStatus.HTTP_OK && verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                return WxPayKit.jsApiCreateSign(wechatProperties.getAppId(), prepayId, wechatProperties.getPay().getCertKeyPath());
            } else {
                log.error("微信商户号查询订单失败,状态码：{}, 响应信息：{}", response.getStatus(), response.getBody());
                throw new RuntimeException("微信支付接口调用失败");
            }
        } catch (Exception e) {
            log.error("微信支付接口调用失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 小程序支付结果通知
     *
     * @param request  请求
     * @param response 响应
     * @return 通知结果
     */
    public MiniAppPayNotifyVo miniAppPayNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");
            String result = NetWorkUtil.readData(request);
            // 通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wechatProperties.getPay().getMchKey(), wechatProperties.getPay().getPlatformPath());
            //当获取到数据后，进行请求返回
            if (StrUtil.isNotEmpty(plainText)) {
                response.setStatus(200);
                map.put("code", "SUCCESS");
                map.put("message", "SUCCESS");
            } else {
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            }
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
            return JSONUtil.toBean(plainText, MiniAppPayNotifyVo.class);
        } catch (Exception e) {
            log.error("微信支付通知接口调用失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 商户订单号查询订单
     * 推荐使用方法：在微信支付时将单号存到redis中，
     * 在接收到回调信息后删除redis中的信息
     * 定时判断redis中是否有订单，如果有，则查询订单
     *
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    public MiniAppPayNotifyVo miniAppQueryOrder(String outTradeNo) {
        try {
            Map<String, String> params = new HashMap<>(16);
            params.put("mchid", wechatProperties.getPay().getMchId());
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.getDomain(),
                    String.format(BasePayApiEnum.ORDER_QUERY_BY_OUT_TRADE_NO.getUrl(), outTradeNo),
                    wechatProperties.getPay().getMchId(),
                    getSerialNumber(),
                    null,
                    wechatProperties.getPay().getCertKeyPath(),
                    params
            );
            //根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wechatProperties.getPay().getPlatformPath());
            log.info("微信支付接口调用结果：{}", verifySignature);
            if (response.getStatus() == HttpStatus.HTTP_OK && verifySignature) {
                return JSONUtil.toBean(response.getBody(), MiniAppPayNotifyVo.class);
            } else {
                log.error("微信商户号查询订单失败,状态码：{}, 响应信息：{}", response.getStatus(), response.getBody());
                throw new RuntimeException("微信支付接口调用失败");
            }

        } catch (Exception e) {
            log.error("微信商户号查询订单失败",e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取证书序列号
     *
     * @return 证书序列号
     */
    private String getSerialNumber() {
        X509Certificate cert = PayKit.getCertificate(wechatProperties.getPay().getCertPath());
        if (cert == null) {
            // 证书不存在
            throw new RuntimeException("缺少apiclient_cert.pem证书");
        }
        //如果证书存在，则获取序列号
        //证书存在
        String serialNo = cert.getSerialNumber().toString(16).toUpperCase();
        //检查证书是否有效
        boolean isValid = PayKit.checkCertificateIsValid(cert, wechatProperties.getPay().getMchId(), -2);
        DateTime notAfter = DateUtil.date(cert.getNotAfter());
        if (isValid && notAfter.after(DateUtil.date())) {
            //证书有效,且时间未到期
            //判断是否保存了该文件，如果不存在，则重新获取证书并保存
            if (FileUtil.isEmpty(new File(wechatProperties.getPay().getPlatformPath()))) {
                getNewCertPath(serialNo);
            }
            return serialNo;
        } else {
            log.info("证书是否可用 {} 证书有效期为 {}", isValid, DateUtil.format(notAfter, DatePattern.NORM_DATETIME_PATTERN));
            throw new RuntimeException("apiclient_cert.pem证书无效");
        }

    }

    /**
     * 获取新证书
     *
     * @param serialNo 证书序列号
     */
    private void getNewCertPath(String serialNo) {
        log.info("重新生成证书");
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.getDomain(),
                    OtherApiEnum.GET_CERTIFICATES.getUrl(),
                    wechatProperties.getPay().getMchId(),
                    serialNo,
                    null,
                    wechatProperties.getPay().getCertKeyPath(),
                    ""
            );
            String body = response.getBody();
            if (response.getStatus() == HttpStatus.HTTP_OK) {
                boolean verifySignature = WxPayKit.verifySignature(response, wechatProperties.getPay().getPlatformPath());
                if (verifySignature) {
                    log.info("获取证书成功");
                } else {
                    throw new RuntimeException("获取证书失败");
                }
                JSONObject jsonObject = JSONUtil.parseObj(body);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                //判断证书数量（证书数量大于1时，说明需要更换证书）
                switch (dataArray.size()) {
                    case 1: {
                        // 默认认为只有一个平台证书
                        JSONObject encryptObject = dataArray.getJSONObject(0);
                        //保存平台证书
                        savePlatformCert(encryptObject);
                        break;
                    }
                    case 2: {
                        //新老证书交替时，取启用时间最新的证书
                        JSONObject encryptObject0 = dataArray.getJSONObject(0);
                        JSONObject encryptObject1 = dataArray.getJSONObject(1);
                        DateTime effectiveTime0 = encryptObject0.get("effective_time", DateTime.class);
                        DateTime effectiveTime1 = encryptObject1.get("effective_time", DateTime.class);
                        if (effectiveTime0.after(effectiveTime1)) {
                            // 取启用时间最新的证书
                            savePlatformCert(encryptObject0);
                        } else {
                            savePlatformCert(encryptObject1);
                        }
                        break;
                    }
                    default: {
                        // 证书数量大于2或者小于1时，证书有问题
                        throw new RuntimeException("证书获取异常");
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取证书失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存平台证书
     *
     * @param encryptObject 加密证书对象
     */
    private void savePlatformCert(JSONObject encryptObject) {
        //【证书信息】 证书内容
        JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
        //【加密证书的附加数据】 加密证书的附加数据，固定为“certificate"。
        String associatedData = encryptCertificate.getStr("associated_data");
        //【加密后的证书内容】 使用API KEY和上述参数，可以解密出平台证书的明文。证书明文为PEM格式。
        // （注意：更换证书时会出现PEM格式中的证书失效时间与接口返回的证书弃用时间不一致的情况）
        String cipherText = encryptCertificate.getStr("ciphertext");
        //【加密证书的随机串】 对应到加密算法中的IV。
        String nonce = encryptCertificate.getStr("nonce");
        try {
            AesUtil aesUtil = new AesUtil(wechatProperties.getPay().getMchKey().getBytes(StandardCharsets.UTF_8));
            // 平台证书密文解密
            // encrypt_certificate 中的  associated_data nonce  ciphertext
            String publicKey = aesUtil.decryptToString(
                    associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8),
                    cipherText
            );
            // 保存证书
            FileWriter writer = new FileWriter(wechatProperties.getPay().getPlatformPath());
            writer.write(publicKey);
        } catch (Exception e) {
            log.error("保存平台证书失败", e);
            throw new RuntimeException(e);
        }

    }
}

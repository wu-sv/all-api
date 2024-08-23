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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tamako
 */
@Slf4j
public class WeChatPayApi {
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
     * 获取证书序列号
     *
     * @return 证书序列号
     */
    private String getSerialNumber() {
        X509Certificate cert = PayKit.getCertificate(wechatProperties.getPay().getCertPath());
        //如果证书存在，则获取序列号
        if (cert != null) {
            String serialNo = cert.getSerialNumber().toString(16).toUpperCase();
            // 提前两天检查证书是否有效
            boolean isValid = PayKit.checkCertificateIsValid(cert, wechatProperties.getPay().getMchId(), -2);
            DateTime notAfter = DateUtil.date(cert.getNotAfter());
            log.info("证书是否可用 {} 证书有效期为 {}", isValid, DateUtil.format(notAfter, DatePattern.NORM_DATETIME_PATTERN));
            File file = new File(wechatProperties.getPay().getPlatformPath());
            if (FileUtil.isEmpty(file)) {
                getNewCertPath(serialNo);
            }
            return serialNo;
        }
        return null;

    }

    /**
     * 获取新证书
     *
     * @return 证书
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
//            String serialNumber = response.getHeader("Wechatpay-Serial");
            String body = response.getBody();
            String platSerialNo = "";
            if (response.getStatus() == HttpStatus.HTTP_OK) {
                JSONObject jsonObject = JSONUtil.parseObj(body);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                // 默认认为只有一个平台证书
                JSONObject encryptObject = dataArray.getJSONObject(0);
                //【证书序列号】 平台证书的主键，唯一定义此资源的标识
                serialNo = encryptObject.getStr("serial_no");
                //【证书信息】 证书内容
                JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
                //【加密证书的附加数据】 加密证书的附加数据，固定为“certificate"。
                String associatedData = encryptCertificate.getStr("associated_data");
                //【加密后的证书内容】 使用API KEY和上述参数，可以解密出平台证书的明文。证书明文为PEM格式。
                // （注意：更换证书时会出现PEM格式中的证书失效时间与接口返回的证书弃用时间不一致的情况）
                String cipherText = encryptCertificate.getStr("ciphertext");
                //【加密证书的随机串】 对应到加密算法中的IV。
                String nonce = encryptCertificate.getStr("nonce");
                //保存平台证书
                platSerialNo = savePlatformCert(associatedData, nonce, cipherText, wechatProperties.getPay().getPlatformPath());
            }
            boolean verifySignature = WxPayKit.verifySignature(response, wechatProperties.getPay().getPlatformPath());
            if (verifySignature) {
                log.info("获取证书成功");
            } else {
                throw new RuntimeException("获取证书失败");
            }
        } catch (Exception e) {
            log.error("获取证书失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存平台证书
     *
     * @param associatedData 加密证书的附加数据
     * @param nonce          加密证书的随机串
     * @param cipherText     加密后的证书内容
     * @param platformPath   平台证书保存路径
     * @return 证书序列号
     */
    private String savePlatformCert(String associatedData, String nonce, String cipherText, String platformPath) {
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
            FileWriter writer = new FileWriter(platformPath);
            writer.write(publicKey);
            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(new ByteArrayInputStream(publicKey.getBytes()));
            return certificate.getSerialNumber().toString(16).toUpperCase();
        } catch (Exception e) {
            log.error("保存平台证书失败", e);
            throw new RuntimeException(e);
        }

    }
}

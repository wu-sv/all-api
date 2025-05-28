package com.tamako.allapi.utils;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.OtherApiEnum;
import com.ijpay.wxpay.model.v3.Certificate;
import com.ijpay.wxpay.model.v3.CertificateInfo;
import com.ijpay.wxpay.model.v3.EncryptCertificate;
import com.tamako.allapi.configuration.properties.WechatProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import com.tamako.allapi.wechat.model.wxpay.domin.PlatFromCert;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;

/**
 * 微信支付工具类
 *
 * @author Tamako
 * @since 2025/5/27 16:42
 */
public class WxPayUtil {
    /**
     * 保存平台证书
     *
     * @param platFromCert    平台证书信息
     * @param certificateInfo 加密证书对象
     * @param mchKey          商户密钥
     * @throws Exception 保存证书失败
     */
    public static void savePlatformCert(CertificateInfo certificateInfo, PlatFromCert platFromCert, String mchKey) throws Exception {
        //【证书信息】 证书内容
        EncryptCertificate encryptCertificate = certificateInfo.getEncrypt_certificate();
        //【加密证书的附加数据】 加密证书的附加数据，固定为"certificate"。
        String associatedData = encryptCertificate.getAssociated_data();
        //【加密后的证书内容】 使用API KEY和上述参数，可以解密出平台证书的明文。证书明文为PEM格式。
        // （注意：更换证书时会出现PEM格式中的证书失效时间与接口返回的证书弃用时间不一致的情况）
        String cipherText = encryptCertificate.getCiphertext();
        //【加密证书的随机串】 对应到加密算法中的IV。
        String nonce = encryptCertificate.getNonce();

        AesUtil aesUtil = new AesUtil(mchKey.getBytes(StandardCharsets.UTF_8));
        // 平台证书密文解密
        // encrypt_certificate 中的  associated_data nonce  ciphertext
        String platformCertKey = aesUtil.decryptToString(
                associatedData.getBytes(StandardCharsets.UTF_8),
                nonce.getBytes(StandardCharsets.UTF_8),
                cipherText
        );
        // 保存证书
        // 保存证书
        platFromCert.setCert(platformCertKey)
                .setExpireTime(DateTime.now().offset(DateField.HOUR, 11));
    }

    /**
     * 获取证书序列号
     *
     * @param platFromCert 平台证书信息
     * @param certPath     证书路径
     * @param mchId        商户号
     */
    public static void getSerialNo(PlatFromCert platFromCert, String certPath, String mchId) {
        X509Certificate cert = PayKit.getCertificate(certPath);
        //检查证书是否有效
        boolean isValid = PayKit.checkCertificateIsValid(cert, mchId, -2);
        if (isValid) {
            //如果证书存在，则获取序列号
            //证书存在
            //证书有效,且时间未到期
            //判断是否保存了该文件，如果不存在，则重新获取证书并保存
            platFromCert.setSerialNo(cert.getSerialNumber().toString(16).toUpperCase());
        } else {
            throw new AllApiException(PlatformEnum.WX, "apiclient_cert.pem证书无效");
        }
    }

    /**
     * 验证签名
     *
     * @param response     响应
     * @param platFromCert 平台证书信息
     * @param pay          微信支付配置
     */
    public static void verifySignature(IJPayHttpResponse response, PlatFromCert platFromCert, WechatProperties.Pay pay) {
        try {
            //校验平台证书持有时间
            if (DateTime.now().after(platFromCert.getExpireTime())) {
                //需要获取一个新的证书
                downloadCertificateInfo(platFromCert, pay);
            }
            boolean verifySignature = WxPayKit.verifySignature(response, platFromCert.getCert());
            if (!verifySignature) {
                throw new AllApiException(PlatformEnum.WX, "验证签名失败");
            }
        } catch (Exception e) {
            throw new AllApiException(PlatformEnum.WX, "验证签名失败");
        }
    }

    /**
     * 下载平台证书
     *
     * @param platFromCert 平台证书信息
     * @param pay          微信支付配置
     * @throws Exception 获取证书失败
     */
    public static void downloadCertificateInfo(@NotNull PlatFromCert platFromCert, WechatProperties.Pay pay) throws Exception {
        IJPayHttpResponse response = WxPayApi.v3(
                RequestMethodEnum.GET,
                WxDomainEnum.CHINA.getDomain(),
                OtherApiEnum.GET_CERTIFICATES.getUrl(),
                pay.getMchId(),
                platFromCert.getSerialNo(),
                null,
                pay.getCertKeyPath(),
                ""
        );
        if (response.getStatus() != HttpStatus.HTTP_OK) {
            throw new AllApiException(PlatformEnum.WX, "证书获取异常");
        }
        String body = response.getBody();
        //第一次不需要验证签名
        if (ObjUtil.isNotEmpty(platFromCert.getCert())) {
            //如果为空则不进行后续校验
            verifySignature(response, platFromCert, pay);
        }
        Certificate certificate = JSONUtil.toBean(body, Certificate.class);
        //判断证书数量（证书数量大于1时，说明需要更换证书）
        CertificateInfo certificateInfo;
        switch (certificate.getData().size()) {
            // 默认认为只有一个平台证书
            case 1 -> certificateInfo = certificate.getData().get(0);
            case 2 -> {
                //新老证书交替时，取启用时间最新的证书
                CertificateInfo certificateInfo0 = certificate.getData().get(0);
                CertificateInfo certificateInfo1 = certificate.getData().get(1);
                DateTime effectiveTime0 = cn.hutool.core.date.DateUtil.parseTime(certificateInfo0.getEffective_time());
                DateTime effectiveTime1 = DateUtil.parseTime(certificateInfo1.getEffective_time());
                certificateInfo = certificateInfo0;
                if (effectiveTime0.before(effectiveTime1)) {
                    // 取启用时间最新的证书
                    certificateInfo = certificateInfo1;
                }
            }
            // 证书数量大于2或者小于1时，证书有问题
            default -> throw new AllApiException(PlatformEnum.WX, "证书获取异常");
        }
        WxPayUtil.savePlatformCert(certificateInfo, platFromCert, pay.getMchKey());
    }
}

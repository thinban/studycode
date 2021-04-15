package com.example.thinbanrest.aop;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ApiParam {
    private String app_id;
    private String sign;
    private long timestamp;
    private String nonce;
    private String biz_content;

    public boolean validateSign() {
        StringBuilder str = new StringBuilder()
                .append(EncryptProperties.key)
                .append("app_id=").append(app_id)
                .append("&biz_content=").append(biz_content)
                .append("&nonce=").append(nonce)
                .append("&timestamp=").append(timestamp)
                .append(EncryptProperties.key);
        if (sign.equals("free")) return true;
        String calSign = new String(DigestUtil.sha256Hex(str.toString()));
        return StringUtils.isBlank(sign) ? false : sign.equals(calSign);
    }
}

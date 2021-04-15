package com.example.thinbanrest.core;


/**
 * thinban
 */
public enum SysMsg {
    /**
     * ------------
     */
    SYS_SUCCESS("000000", "SUCCESS"),
    SYS_ERROR("100001", "服务繁忙,请稍候再试"),
    USERNAME_SERCET_ERROR("100011", "帐号密码错误"),
    USER_NOT_EXIST("100012", "用户不存在"),
    USER_HAD_EXIST("100013", "用户已存在"),
    WECHAT_BIND_ERROR("100021", "未绑定微信帐号"),
    TOKEN_EXPIRED("200001", "登录过期"),
    QUERY_NULL("300001", "没有更多信息了"),
    FORBIDDEN("400003", "您的权限不足"),
    NET_ERROR("400004", "网络连接错误"),
    NOTICE("500000", "弹出公告"),
    NOTICE_NOT("500001", "不弹出公告"),
    /**
     * 800000 订单相关 ------------
     */
    /**
     * 参数为空
     */
    ORDER_PARAM_NULL("800001", "参数不正确（代码:800001）"),
    /**
     * 寄件地区为空
     */
    ORDER_SEND_AREA_NULL("800002", "参数不正确（代码:800002）"),
    /**
     * 收件地区为空
     */
    ORDER_RECEIVE_AREA_NULL("800003", "参数不正确（代码:800003）"),
    /**
     * 重量不能小于0g，不能超过9999000g（9999kg）
     * 标准快递重量限制40kg以下
     * 快递包裹重量限制20kg以下
     */
    ORDER_WEIGHT_CHECK("800004", "参数不正确（代码:800004）"),
    /**
     * 长度不能小于0cm，不能超过99900cm
     */
    ORDER_LENGTH_CHECK("800005", "参数不正确（代码:800005）"),
    /**
     * 姓名：2~20最大，过滤特殊字符
     */
    ORDER_NAME_CHECK("800006", "参数不正确（代码:800006）"),
    /**
     * 电话：20 最大，根据手机、固话、客服电话规则限制格式，过滤特殊字符
     * 如果是11位并且1开头则校验手机号，否则判断固话规则（带分机号）不能超过2位分隔符
     */
    ORDER_PHONE_CHECK("800007", "参数不正确（代码:800007）"),
    /**
     * 详细地址：100最大，前端控制过滤部分特殊字符
     */
    ORDER_ADDRESS_CHECK("800008", "参数不正确（代码:800008）"),
    /**
     * 商品描述，不能超过15位
     */
    ORDER_GOODS_DESC_CHECK("800009", "参数不正确（代码:800009）"),
    /**
     * 保价金额不能小于0
     */
    ORDER_INSURANCE_CHECK("800010", "参数不正确（代码:800010）"),
    /**
     * 预估运费金额不能小于0
     */
    ORDER_PREDICT_PRICE_CHECK("800011", "参数不正确（代码:800011）"),
    /**
     * 参数非数字
     */
    ORDER_NUMBER_CHECK("800012", "参数不正确（代码:800012）"),


    TOKEN_FAIL("200001", "TOKEN校验失败"),
    SILENCE_RENDER("900007", ""),
    PARAMETER_NULL("900008", "参数为空"),
    /**
     * 参数错误
     */
    PARAMETER_ERROR("900009", "参数错误"),;

    /**
     * 错误编号
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;

    SysMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(String code) {
        for (SysMsg c : SysMsg.values()) {
            if (code.equals(c.getCode())) {
                return c.getMsg();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

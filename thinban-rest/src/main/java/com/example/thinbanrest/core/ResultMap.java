package com.example.thinbanrest.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * thinban
 *
 * @param <T>
 */
public class ResultMap<T> implements Serializable {

    /**
     * @api {private} ResultMap.-code ResultMap.-code
     * @apiName ParaMap.-code
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription code属性 String
     */
    private String code;

    /**
     * @api {private} ResultMap.-msg ResultMap.-msg
     * @apiName ResultMap.-msg
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription msg属性 String
     */
    private String msg;

    /**
     * @api {private} ResultMap.-info ResultMap.-info
     * @apiName ParaMap.-info
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription info属性 Object
     */
    private T info;

    public static ResultMap parse(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        ResultMap rs = new ResultMap();
        rs.setMsg(jsonObject.getString("msg"));
        rs.setCode(jsonObject.getString("code"));
        rs.setInfo(jsonObject.get("info"));
        return rs;
    }

    /**
     * @api {public} ResultMap.ResultMap() ResultMap.ResultMap()
     * @apiName ParaMap.ResultMap()
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 构造函数, 初始为失败信息
     */
    public ResultMap() {
        setMsg(SysMsg.SYS_ERROR);
    }

    /**
     * @api {public} ResultMap.getCode() ResultMap.getCode()
     * @apiName ResultMap.getCode()
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 获取返回结果代码号
     * @apiSuccess (return) String
     */
    public String getCode() {
        return code;
    }

    /**
     * @api {public} ResultMap.setCode(code) ResultMap.setCode(String code)
     * @apiName ResultMap.setCode(String code)
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 设置结果编号
     * @apiParam {String} code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @api {public} ResultMap.getMsg() ResultMap.getMsg()
     * @apiName ResultMap.getMsg()
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 获取返回结果描述
     * @apiSuccess (return) String
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @api {public} ResultMap.setMsg(msg) ResultMap.setMsg(SysMsg msg)
     * @apiName ResultMap.setMsg(SysMsg msg)
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 设置消息
     * @apiParam {SysMsg} msg 系统消息类型
     */
    public void setMsg(SysMsg msg) {
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }

    /**
     * @api {public} ResultMap.setMsg(msg) ResultMap.setMsg(String msg)
     * @apiName ResultMap.setMsg(String msg)
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 设置消息描述
     * @apiParam {String} msg消息描述
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @api {public} ResultMap.getInfo() ResultMap.getInfo()
     * @apiName ResultMap.getInfo()
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 获取消息信息
     * @apiSuccess (return) Object
     */
    public T getInfo() {
        return info;
    }

    /**
     * @api {public} ResultMap.setInfo(info) ResultMap.setInfo(Object info)
     * @apiName ResultMap.setInfo(Object info)
     * @apiGroup com.yeeknet.common.kit
     * @apiVersion 0.1.0
     * @apiDescription 设置结果消息信息
     * @apiParam {Object} info 消息信息
     */
    public void setInfo(T info) {
        this.info = info;
    }
}


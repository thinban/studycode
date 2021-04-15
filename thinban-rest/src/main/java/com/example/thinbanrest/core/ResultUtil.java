package com.example.thinbanrest.core;

import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

/**
 * 结果集工具类
 *
 * @author thinban
 * @date 2017-12-24
 */
public class ResultUtil {


    public static ResultMap success(Object info) {
        ResultMap rs = new ResultMap();
        rs.setMsg(SysMsg.SYS_SUCCESS);
        rs.setInfo(info);
        return rs;
    }

    public static ResultMap success() {
        ResultMap rs = new ResultMap();
        rs.setMsg(SysMsg.SYS_SUCCESS);
        return rs;
    }

    public static ResultMap notice(Object info) {
        ResultMap rs = new ResultMap();
        rs.setCode(SysMsg.NOTICE.getCode());
        rs.setMsg(SysMsg.NOTICE);
        rs.setInfo(info);
        return rs;
    }

    public static ResultMap noNotice() {
        ResultMap rs = new ResultMap();
        rs.setCode(SysMsg.NOTICE_NOT.getCode());
        rs.setMsg(SysMsg.NOTICE_NOT);
        return rs;
    }

    /**
     * 成功并返回消息
     *
     * @param msg
     * @return
     */
    public static ResultMap successWithMsg(String msg) {
        ResultMap rs = success();
        rs.setMsg(msg);
        return rs;
    }

    public static ResultMap error(String code, String msg) {
        ResultMap result = new ResultMap();
        result.setMsg(msg);
        result.setCode(code);
        return result;
    }

    public static ResultMap error(String code, String msg, Object object) {
        ResultMap result = new ResultMap();
        result.setMsg(msg);
        result.setCode(code);
        result.setInfo(object);
        return result;
    }

    public static ResultMap error(SysMsg sysMsg) {
        ResultMap result = new ResultMap();
        result.setMsg(sysMsg.getMsg());
        result.setCode(sysMsg.getCode());
        return result;
    }

    public static ResultMap error() {
        return new ResultMap();
    }

    public static ResultMap error(String msg) {
        ResultMap result = new ResultMap();
        result.setMsg(msg);
        return result;
    }

    /**
     * token校验失败
     *
     * @return
     */
    public static ResultMap tokenFail() {
        ResultMap result = new ResultMap();
        result.setMsg(SysMsg.TOKEN_FAIL);
        return result;
    }

    public static ResultMap netError() {
        ResultMap result = new ResultMap();
        result.setMsg(SysMsg.NET_ERROR);
        return result;
    }

    public static ResultMap forbidden() {
        ResultMap result = new ResultMap();
        result.setMsg(SysMsg.FORBIDDEN);
        return result;
    }

    public static ResultMap tokenExpire() {
        return error(SysMsg.TOKEN_EXPIRED);
    }

    public static ResultMap hasNoMore() {
        return error(SysMsg.QUERY_NULL);
    }


    public static ResultMap parameterNull() {
        return error(SysMsg.PARAMETER_NULL);
    }

    public static ResultMap parameterError() {
        return error(SysMsg.PARAMETER_ERROR);
    }

    /**
     * 判断处理结果集是否成功
     *
     * @param rs
     * @return
     */
    public static boolean isSuccess(ResultMap rs) {
        return rs != null && rs.getCode().equals(SysMsg.SYS_SUCCESS.getCode());
    }

    /**
     * 判断处理结果集是否成功
     *
     * @param rs
     * @return
     */
    public static boolean notSuccess(ResultMap rs) {
        return rs == null || (!rs.getCode().equals(SysMsg.SYS_SUCCESS.getCode()));
    }


    /**
     * 返回异步调用结果
     *
     * @param value
     * @return
     */
    public static Future asyncResult(Object value) {
        return new AsyncResult(value);
    }

}

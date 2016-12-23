package com.pzj.xdemo.login.bean;

import java.io.Serializable;

/**
 * Created by pzj on 2016/12/14.
 */

public class BaseBean implements Serializable {
    protected int ResultCode;
    protected String Message;

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}

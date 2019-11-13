package cn.sts.base.model.server.vo;

/**
 * 请求服务器返回的统一数据结构
 * <p>
 * Created by weilin on 16/11/15.
 */

public class RequestResult<T> {

    /**
     * 状态，成功/失败
     */
    private boolean success;

    /**
     * 返回的数据
     */
    private T obj;

    /**
     * 服务器错误信息
     */
    private String msg;

    /**
     * 返回码
     */
    private Integer code;

    private String jessionId;

    public String getMsg() {
        return msg;
    }


    public boolean getSuccess() {
        return success;
    }

    public T getObj() {
        return obj;
    }

    public Integer getCode() {
        return code;
    }

    public String getJessionId() {
        return jessionId;
    }
}

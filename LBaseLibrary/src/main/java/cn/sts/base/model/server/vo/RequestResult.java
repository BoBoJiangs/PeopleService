package cn.sts.base.model.server.vo;

/**
 * 请求服务器返回的统一数据结构
 * <p>
 * Created by weilin on 16/11/15.
 */

public class RequestResult<T> {

    private int error_code;
    /**
     * 状态，成功/失败
     */
    private boolean success;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 服务器错误信息
     */
    private String message;

    /**
     * 返回码
     */
    private Integer status;

    private String jessionId;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return message;
    }


    public boolean getSuccess() {
        return success;
    }

    public T getObj() {
        return data;
    }

    public Integer getCode() {
        return status;
    }

    public String getJessionId() {
        return jessionId;
    }
}

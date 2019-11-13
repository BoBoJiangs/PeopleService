package cn.sts.base.model.server.exception;

/**
 * 错误信息，统一处理
 * <p>
 * Created by weilin on 16/11/16.
 */
public class RequestException extends RuntimeException {

    /**
     * 错误信息的构造方法
     *
     * @param errorCode 错误码
     */
    public RequestException(int errorCode) {
        this(codeToErrorMessage(errorCode));
    }

    /**
     * 错误信息的构造方法
     *
     * @param errorMessage 错误信息
     */
    public RequestException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * 将错误代码转换成友好的显示信息
     *
     * @param errorCode 错误码
     */
    private static String codeToErrorMessage(int errorCode) {
        String errorMessage;
        switch (errorCode) {
            case 0:
                errorMessage = "操作失败";
                break;
            default:
                errorMessage = "操作失败";
                break;
        }
        return errorMessage;
    }
}


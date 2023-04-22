package com.soufang.soufangdemo.base;

/**
 * 业务异常代表本项目内逻辑代码所产生的异常，且开发者认为代码不可自动修复
 * <p>
 * 友好提示： 给到调用侧排查错误码
 * 错误码：给到调用侧排查错误
 */
public class BusinessException extends RuntimeException {
    private static final Status COMMON_STATUS = Status.UNKNOWN;
    private int code = COMMON_STATUS.getCode();

    public BusinessException(Throwable t) {
        super(t);
        if (t instanceof BusinessException) {
            this.code = ((BusinessException) t).code;
        }
    }

    public BusinessException(String message, Throwable t) {
        super(message, t);
        if (t instanceof BusinessException) {
            this.code = ((BusinessException) t).code;
        }
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Status status) {
        super(status.getMessage());
        this.code = status.getCode();
    }

    public BusinessException(Status status, Throwable t) {
        super(status.getMessage(), t);
        this.code = status.getCode();
    }

    public int getCode() {
        return code;
    }
}

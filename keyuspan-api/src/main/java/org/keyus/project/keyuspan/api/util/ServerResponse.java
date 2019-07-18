package org.keyus.project.keyuspan.api.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author keyus
 * @create 2019-07-18  上午7:22
 * 为了封装自定义信息而设计的服务响应类
 */
// 加上一个空参构造方法，保证对象序列化与反序列化能正常工作
// 否则，将导致JackSon包序列化或反序列化产生异常
@NoArgsConstructor
public class ServerResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter private int status;
    @Getter private String msg;
    @Getter private T data;

    private ServerResponse(ResponseCode responseCode, T data) {
        this.status = responseCode.getCode();
        this.msg = responseCode.getDesc();
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    // 只要响应不是error，都视为success
    public static boolean isSuccess(ServerResponse response) {
        return response != null && response.status != ResponseCode.ERROR.getCode();
    }

    public static boolean isNullValue(ServerResponse response) {
        return response != null && response.status == ResponseCode.NULL_VALUE.getCode();
    }

    public static <T> ServerResponse<T> createBySuccessWithoutData() {
        return new ServerResponse<>(ResponseCode.SUCCESS, null);
    }

    public static <T> ServerResponse<T> createBySuccessWithData(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS, data);
    }

    public static <T> ServerResponse<T> createBySuccessMessageWithoutData(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static <T> ServerResponse<T> createBySuccessMessageWithData(String msg, T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createBySuccessNullValue() {
        return new ServerResponse<>(ResponseCode.NULL_VALUE, null);
    }

    public static <T> ServerResponse<T> createByErrorWithoutMessage() {
        return new ServerResponse<>(ResponseCode.ERROR, null);
    }

    public static <T> ServerResponse<T> createByErrorWithMessage(String msg) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static <T> ServerResponse<T> createByErrorIllegalParameters(String[] parameters) {
        StringBuilder builder = new StringBuilder("Error! your parameters: ");
        for (String parameter : parameters) {
            builder.append(parameter);
            builder.append(',');
        }
        // 去掉最后一个逗号
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" is illegal!");
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), builder.toString(), null);
    }

    public static <T> ServerResponse<T> createByNeedLogin() {
        return new ServerResponse<>(ResponseCode.NEED_LOGIN, null);
    }

    public static <T> ServerResponse<T> createAnyServerResponse(int status, String msg, T data) {
        return new ServerResponse<>(status, msg, data);
    }
}

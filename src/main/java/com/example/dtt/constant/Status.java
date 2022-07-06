package com.example.dtt.constant;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Optional;

/**
 * status enum      // todo #4855 One category one interval
 */
public enum Status {

    SUCCESS(0, "success", "成功"),
    CONNECTION_TEST_FAILURE(10037, "connection test failure", "测试数据源连接失败"),
    DATASOURCE_EXIST(10015, "data source name already exists", "数据源名称已存在"),
    DATASOURCE_CONNECT_FAILED(10016, "data source connection failed", "建立数据源连接失败"),
    RESOURCE_NOT_EXIST(20004, "resource not exist", "资源不存在"),
    PARENT_RESOURCE_NOT_EXIST(20015, "parent resource not exist", "父资源文件不存在"),
    FILE_EXIST(20005, "file already exists", "文件已存在，请校验确认"),
    STORE_OPERATE_CREATE_ERROR(1300010, "create the resource failed", "存储操作失败"),
    VERIFY_PARAMETER_NAME_FAILED(1300009, "The file name verify  failed", "文件命名校验失败"),
    USER_NOT_EXIST(30004, "user not exist", "不存在配置的用户"),
    ;

    private final int code;
    private final String enMsg;
    private final String zhMsg;

    Status(int code, String enMsg, String zhMsg) {
        this.code = code;
        this.enMsg = enMsg;
        this.zhMsg = zhMsg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
            return this.zhMsg;
        } else {
            return this.enMsg;
        }
    }

    /**
     * Retrieve Status enum entity by status code.
     * @param code
     * @return
     */
    public static Optional<Status> findStatusBy(int code) {
        for (Status status : Status.values()) {
            if (code == status.getCode()) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}

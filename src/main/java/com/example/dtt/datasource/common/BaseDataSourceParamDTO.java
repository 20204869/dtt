package com.example.dtt.datasource.common;

import com.example.dtt.datasource.common.api.hive.HiveDataSourceParamDTO;
import com.example.dtt.datasource.common.api.mysql.MySQLDataSourceParamDTO;
import com.example.dtt.enums.DbType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * Basic datasource params submitted to api.
 * <p>
 * see {@link MySQLDataSourceParamDTO}
 * see {@link HiveDataSourceParamDTO}
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(value = {
    @JsonSubTypes.Type(value = MySQLDataSourceParamDTO.class, name = "MYSQL"),
    @JsonSubTypes.Type(value = HiveDataSourceParamDTO.class, name = "HIVE"),
})
public abstract class BaseDataSourceParamDTO implements Serializable {

    protected Integer id;

    protected String name;

    protected String note;

    protected String host;

    protected Integer port;

    protected String database;

    protected String userName;

    protected String password;

    protected Map<String, String> other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getOther() {
        return other;
    }

    public void setOther(Map<String, String> other) {
        this.other = other;
    }

    /**
     * Get the datasource type
     * see{@link DbType}
     *
     * @return datasource type code
     */
    public abstract DbType getType();
}

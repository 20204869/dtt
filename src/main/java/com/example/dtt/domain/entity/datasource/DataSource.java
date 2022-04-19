package com.example.dtt.domain.entity.datasource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.dtt.domain.BaseEntity;
import com.example.dtt.enums.DbType;

import java.util.Date;

@TableName("sys_datasource")
public class DataSource extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * data source name
     */
    private String name;

    /**
     * data source type
     */
    private DbType type;

    /**
     * connection parameters
     */
    private String connectionParams;


    public DataSource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DbType getType() {
        return type;
    }

    public void setType(DbType type) {
        this.type = type;
    }

    public String getConnectionParams() {
        return connectionParams;
    }

    public void setConnectionParams(String connectionParams) {
        this.connectionParams = connectionParams;
    }

    @Override
    public String toString() {
        return "DataSource{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", type=" + type
                + ", connectionParams='" + connectionParams + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataSource that = (DataSource) o;

        if (id != that.id) {
            return false;
        }
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}

package com.example.dtt.domain.entity.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.dtt.domain.BaseEntity;
import com.example.dtt.enums.DbType;

@TableName("sys_hive_user")
public class HiveUser extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * user name
     */
    private String userName;

    /**
     * connection parameters
     */
    private String connectionParams;


    public HiveUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConnectionParams() {
        return connectionParams;
    }

    public void setConnectionParams(String connectionParams) {
        this.connectionParams = connectionParams;
    }

    @Override
    public String toString() {
        return "HiveUser{"
                + "id=" + id
                + ", name='" + userName + '\''
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

        HiveUser that = (HiveUser) o;

        if (id != that.id) {
            return false;
        }
        return userName.equals(that.userName);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userName.hashCode();
        return result;
    }
}

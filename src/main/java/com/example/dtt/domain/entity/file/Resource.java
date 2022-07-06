package com.example.dtt.domain.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.dtt.domain.BaseEntity;

public class Resource extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * parent id
     */
    private int pid;

    /**
     * full name
     */
    private String fullName;

    /**
     * is directory
     */
    private boolean isDirectory = false;

    /**
     * file name
     */
    private String fileName;

    /**
     * resource size
     */
    private long size;


    public Resource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "Resource{" +
            "id=" + id +
            ", pid=" + pid +
            ", fullName='" + fullName + '\'' +
            ", isDirectory=" + isDirectory +
            ", fileName='" + fileName + '\'' +
            ", size=" + size +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource resource = (Resource) o;

        if (id != resource.id) {
            return false;
        }
        return fileName.equals(resource.fileName);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + fileName.hashCode();
        return result;
    }
}

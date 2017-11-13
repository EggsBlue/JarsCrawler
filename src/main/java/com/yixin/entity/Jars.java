package com.yixin.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.sql.Timestamp;

@Table("jars")

public class Jars {
    @Id
    private int id;//编号
    @Column()
    private String name; //jar包名字
    @Column
    private String type;//资源类型,jar,doc,source,pom
    @Column
    private String downurl; //下载地址
    @Column
    private Timestamp create_time;//创建时间


    public Jars(String name, String type, String downurl, Timestamp create_time) {
        this.name = name;
        this.type = type;
        this.downurl = downurl;
        this.create_time = create_time;
    }

    public Jars() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }
}

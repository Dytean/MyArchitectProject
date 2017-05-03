package com.dan.myarchitectproject.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Author: Dan
 * Date: 2017/4/7 下午4:34
 * Description:
 * PackageName: com.dan.myarchitectproject.db.entity
 * Copyright: 杭州安存网络科技有限公司
 **/
@Entity
public class Person {
    @Id(autoincrement = true)
    @Unique
    private Long id;

    @NotNull
    @Property
    private String name;

    @NotNull
    @Property
    private String sex;

    @NotNull
    @Property
    private String height;

    @Generated(hash = 1398841960)
    public Person(Long id, @NotNull String name, @NotNull String sex,
            @NotNull String height) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.height = height;
    }

    @Generated(hash = 1024547259)
    public Person() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}

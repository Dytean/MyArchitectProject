package com.dan.myarchitectproject.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Author: Dan
 * Date: 2017/4/7 下午4:48
 * Description:
 * PackageName: com.dan.myarchitectproject.db.entity
 * Copyright: 杭州安存网络科技有限公司
 **/
@Entity
public class Company {
    @Id(autoincrement = true)
    @Unique
    private Long id;

    @NotNull
    @Property
    private String companyName;

    @NotNull
    @Property
    private String numbers;

    @Generated(hash = 1739683470)
    public Company(Long id, @NotNull String companyName, @NotNull String numbers) {
        this.id = id;
        this.companyName = companyName;
        this.numbers = numbers;
    }

    @Generated(hash = 1096856789)
    public Company() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNumbers() {
        return this.numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

}

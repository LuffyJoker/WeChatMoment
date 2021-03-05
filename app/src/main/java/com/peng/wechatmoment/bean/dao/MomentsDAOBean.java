package com.peng.wechatmoment.bean.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/11/30 18:05
 * desc   :
 */
@Entity
public class MomentsDAOBean {

    // 设置自增长
    @Id(autoincrement = true)
    private Long id;

    @Index(unique = true) // url 字段值唯一
    private String url;

    private String json;

    @Generated(hash = 1315283942)
    public MomentsDAOBean(Long id, String url, String json) {
        this.id = id;
        this.url = url;
        this.json = json;
    }

    @Generated(hash = 132927780)
    public MomentsDAOBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}

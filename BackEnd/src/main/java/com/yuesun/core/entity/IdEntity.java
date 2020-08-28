package com.yuesun.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yuesun.core.json.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class IdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @JsonSerialize(using = JsonDateSerializer.class)
    @Column(updatable = false)
    protected Date createTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        if (createTime != null) {
            return new Date(createTime.getTime());
        }
        return null;
    }

    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            this.createTime = new Date(createTime.getTime());
        }
    }

}

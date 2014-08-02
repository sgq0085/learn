package com.gqshao.myapp.common.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * <pre>
 * 功能说明：通过程序设置ID的entity基类.
 * </pre>
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String id;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "sys-assigned")    
    @GenericGenerator(name = "sys-assigned", strategy = "assigned")  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

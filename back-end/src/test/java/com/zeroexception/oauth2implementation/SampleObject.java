package com.zeroexception.oauth2implementation;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@Document
public class SampleObject {

    @Id
    private long id;

    private Object object;

    public SampleObject(long id, Object object) {
        this.id = id;
        this.object = object;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

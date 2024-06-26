package com.example.alldocunemtreader.data.model;

/**
 * Author: Eccentric
 * Created on 2024/6/24 11:47.
 * Description: com.example.alldocunemtreader.data.model.EventBusMessage
 */
public class EventBusMessage<T> {
    private Integer code;
    private T message;

    public EventBusMessage(Integer code, T message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}

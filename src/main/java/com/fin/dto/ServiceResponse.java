package com.fin.dto;

import java.util.List;

public class ServiceResponse <Type>{
    private String msg;
    private Type object;
    private List<Type> objects;
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ServiceResponse(String msg) {
        this.msg = msg;
        this.object = null;
        this.status=true;
    }

    public ServiceResponse(String msg, boolean status) {
        this.msg = msg;
        this.object = null;
        this.status=status;
    }

    public ServiceResponse(String msg, Type object, boolean status) {
        this.msg = msg;
        this.object = object;
        this.status=status;
    }

    public ServiceResponse(String msg, List<Type> objects, boolean status) {
        this.msg = msg;
        this.objects = objects;
        this.status=status;
    }

    public ServiceResponse(Type object) {
        this.object = object;
        this.objects=null;
    }

    public ServiceResponse(List<Type> objects) {
        this.objects = objects;
        this.object=null;
    }

    public List<Type> getObjects() {
        return objects;
    }

    public void setObjects(List<Type> objects) {
        this.objects = objects;
    }

    public Type getObject() {
        return object;
    }

    public void setObject(Type object) {
        this.object = object;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

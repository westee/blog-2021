package com.westee.blog2021.entity;

public class Result{
    private Status status;
    private String msg;
    private User data;

    public Result(Status status, String msg, User data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static Result SuccessResult(String msg, User user){
        return new Result(Status.OK, msg, user);
    }

    public static Result FailResult(String msg, User user){
        return new Result(Status.FAIL, msg, user);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
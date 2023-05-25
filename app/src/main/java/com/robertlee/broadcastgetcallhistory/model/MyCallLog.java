package com.robertlee.broadcastgetcallhistory.model;

import java.util.Date;

public class MyCallLog {
    private String phoneNumber;
    private Date callStartTime;
    private int callDuration;

    public MyCallLog(String phoneNumber, Date callStartTime, int callDuration) {
        this.phoneNumber = phoneNumber;
        this.callStartTime = callStartTime;
        this.callDuration = callDuration;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Date callStartTime) {
        this.callStartTime = callStartTime;
    }

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }
}

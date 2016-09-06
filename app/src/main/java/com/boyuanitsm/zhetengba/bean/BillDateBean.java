package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2016/9/6.
 */
public class BillDateBean implements Parcelable{
    //   {"id":"65ff1ced740c11e698d0eca86ba4ba05",
    // "userId":"707b88a470e611e698d0eca86ba4ba05",
    // "operationMode":"0",
    // "amount":-50.00,
    // "invitationMode":null,
    // "inviteUserId":null,
    // "invitatedUserId":null,
    // "progress":"0",
    // "modifyPerson":
    // "707b88a470e611e698d0eca86ba4ba05","modifyTime":1473150728000,"remark":null}

    private String id;
    private String userid;
    private String operationMode;
    private String amount;
    private int invitationMode;
    private String inviteUserId;
    private String invitatedUserId;
    private String progress;
    private String modifyPerson;
    private String modifyTime;

    protected BillDateBean(Parcel in) {
        id = in.readString();
        userid = in.readString();
        operationMode = in.readString();
        amount = in.readString();
        invitationMode = in.readInt();
        inviteUserId = in.readString();
        invitatedUserId = in.readString();
        progress = in.readString();
        modifyPerson = in.readString();
        modifyTime=in.readString();
    }

    public static final Creator<BillDateBean> CREATOR = new Creator<BillDateBean>() {
        @Override
        public BillDateBean createFromParcel(Parcel in) {
            return new BillDateBean(in);
        }

        @Override
        public BillDateBean[] newArray(int size) {
            return new BillDateBean[size];
        }
    };

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvitatedUserId() {
        return invitatedUserId;
    }

    public void setInvitatedUserId(String invitatedUserId) {
        this.invitatedUserId = invitatedUserId;
    }

    public int getInvitationMode() {
        return invitationMode;
    }

    public void setInvitationMode(int invitationMode) {
        this.invitationMode = invitationMode;
    }

    public String getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(String inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public String getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BillDateBean() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userid);
        dest.writeString(operationMode);
        dest.writeString(amount);
        dest.writeInt(invitationMode);
        dest.writeString(inviteUserId);
        dest.writeString(invitatedUserId);
        dest.writeString(progress);
        dest.writeString(modifyPerson);
        dest.writeString(modifyTime);
    }
}
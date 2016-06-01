package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * 用户实体类登录返回来的
 * Created by gxy on 2015/11/25.
 */
@Table(name = "user_table")
public class UserInfo implements Parcelable {
    //{"id":"5ffaf68326f211e69615eca86ba4ba05",
    // "username":"13917702726",
    // "password":"a3f9954d368db0d24ae2303c11658fe0",
    // "name":null,
    // "mobilePhoneKey":null,
    // "phone":null,
    // "bindMobileCount":0,
    // "email":null,
    // "qq":null,
    // "wechat":null,
    // "icon":null,
    // "token":null,
    // "referralCode":null,
    // "myReferralCode":"GDKLFWXOHWEEKJDMVM",
    //"companyName":null,
    // "companyAddr":null,
    // "companyPhone":null,
    // "job":null,
    // "userType":null,
    // "isValid":true,
    // "createPersonId":null,
    // "createTime":1464673311000,
    // "modifyPersonId":null,
    // "modifyTime":1464673311000,
    // "remark":null,
    // "empno":null,
    // "dictId":null,
    // "accredit":0,
    // "birthday":null,
    // "sex":null,
    // "address":null,
    // "petName":null,
    // "homeTown":null,
    // "dictName":null}
    @Column
    private String id;//用户id
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String mobilePhoneKey;
    @Column
    private String phone;

    @Column
    private String bindMobileCount;
    @Column
    private String email;
    @Column
    private String qq;
    @Column
    private String wechat;
    @Column
    private String icon;
    @Column
    private String token;
    @Column
    private String referralCode;//用户id
    @Column
    private String myReferralCode;
    @Column
    private String companyName;
    @Column
    private String companyAddr;
    @Column
    private String companyPhone;
    @Column
    private String job;

    @Column
    private String userType;
    @Column
    private String isValid;
    @Column
    private String createPersonId;
    @Column
    private String createTime;
    @Column
    private String modifyPersonId;
    @Column
    private String modifyTime;

    @Column
    private String remark;
    @Column
    private String empno;
    @Column
    private String dictId;

    @Column
    private String accredit;
    @Column
    private String birthday;
    @Column
    private String sex;
    @Column
    private String address;
    @Column
    private String petName;
    @Column
    private String homeTown;
    @Column
    private String dictName;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        id = in.readString();
        username = in.readString();
        name = in.readString();
        password = in.readString();
        mobilePhoneKey = in.readString();
        phone = in.readString();
        bindMobileCount = in.readString();
        email = in.readString();
        qq = in.readString();
        wechat = in.readString();
        icon = in.readString();
        token = in.readString();
        referralCode = in.readString();
        myReferralCode = in.readString();
        companyName = in.readString();
        companyAddr = in.readString();
        companyPhone = in.readString();
        job = in.readString();
        userType = in.readString();
        isValid = in.readString();
        createPersonId = in.readString();
        createTime = in.readString();
        modifyPersonId = in.readString();
        modifyTime = in.readString();
        remark = in.readString();
        empno = in.readString();
        dictId = in.readString();
        accredit = in.readString();
        birthday = in.readString();
        sex = in.readString();
        address = in.readString();
        petName = in.readString();
        homeTown = in.readString();
        dictName = in.readString();
    }

    public String getAccredit() {
        return accredit;
    }

    public void setAccredit(String accredit) {
        this.accredit = accredit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBindMobileCount() {
        return bindMobileCount;
    }

    public void setBindMobileCount(String bindMobileCount) {
        this.bindMobileCount = bindMobileCount;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static Creator<UserInfo> getCREATOR() {
        return CREATOR;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMobilePhoneKey() {
        return mobilePhoneKey;
    }

    public void setMobilePhoneKey(String mobilePhoneKey) {
        this.mobilePhoneKey = mobilePhoneKey;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getMyReferralCode() {
        return myReferralCode;
    }

    public void setMyReferralCode(String myReferralCode) {
        this.myReferralCode = myReferralCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }


    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(mobilePhoneKey);
        dest.writeString(phone);
        dest.writeString(bindMobileCount);
        dest.writeString(email);
        dest.writeString(qq);
        dest.writeString(wechat);
        dest.writeString(icon);
        dest.writeString(token);
        dest.writeString(referralCode);
        dest.writeString(myReferralCode);
        dest.writeString(companyName);
        dest.writeString(companyAddr);
        dest.writeString(companyPhone);
        dest.writeString(job);
        dest.writeString(userType);
        dest.writeString(isValid);
        dest.writeString(createPersonId);
        dest.writeString(createTime);
        dest.writeString(modifyPersonId);
        dest.writeString(modifyTime);
        dest.writeString(remark);
        dest.writeString(empno);
        dest.writeString(dictId);
        dest.writeString(accredit);
        dest.writeString(birthday);
        dest.writeString(sex);
        dest.writeString(address);
        dest.writeString(petName);
        dest.writeString(homeTown);
        dest.writeString(dictName);
    }
}

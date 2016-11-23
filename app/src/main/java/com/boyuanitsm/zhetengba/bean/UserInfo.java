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
    // "dictName":null
    // "city":null}
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

    @Column
    private boolean friend;
    @Column
    private String city;//城市编码后面加的

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String hUsername;//环信账号
    private String hPassword;//环信密码

    private String sameLabels;//共同标签
    private Integer sameCircleCounts;//共同圈子数
    private String balance;//钱数

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public UserInfo() {
    }

    public Integer getSameCircleCounts() {
        return sameCircleCounts;
    }

    public void setSameCircleCounts(Integer sameCircleCounts) {
        this.sameCircleCounts = sameCircleCounts;
    }

    public String getSameLabels() {
        return sameLabels;
    }

    public void setSameLabels(String sameLabels) {
        this.sameLabels = sameLabels;
    }

    public String gethUsername() {
        return hUsername;
    }

    public void sethUsername(String hUsername) {
        this.hUsername = hUsername;
    }


    public String gethPassword() {
        return hPassword;
    }

    public void sethPassword(String hPassword) {
        this.hPassword = hPassword;

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "accredit='" + accredit + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mobilePhoneKey='" + mobilePhoneKey + '\'' +
                ", phone='" + phone + '\'' +
                ", bindMobileCount='" + bindMobileCount + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", icon='" + icon + '\'' +
                ", token='" + token + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", myReferralCode='" + myReferralCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyAddr='" + companyAddr + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", job='" + job + '\'' +
                ", userType='" + userType + '\'' +
                ", isValid='" + isValid + '\'' +
                ", createPersonId='" + createPersonId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyPersonId='" + modifyPersonId + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", remark='" + remark + '\'' +
                ", empno='" + empno + '\'' +
                ", dictId='" + dictId + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", petName='" + petName + '\'' +
                ", homeTown='" + homeTown + '\'' +
                ", dictName='" + dictName + '\'' +
                ", friend=" + friend +
                ", city='" + city + '\'' +
                ", hUsername='" + hUsername + '\'' +
                ", hPassword='" + hPassword + '\'' +
                ", sameLabels='" + sameLabels + '\'' +
                ", sameCircleCounts=" + sameCircleCounts +
                ", balance='" + balance + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.password);
        dest.writeString(this.mobilePhoneKey);
        dest.writeString(this.phone);
        dest.writeString(this.bindMobileCount);
        dest.writeString(this.email);
        dest.writeString(this.qq);
        dest.writeString(this.wechat);
        dest.writeString(this.icon);
        dest.writeString(this.token);
        dest.writeString(this.referralCode);
        dest.writeString(this.myReferralCode);
        dest.writeString(this.companyName);
        dest.writeString(this.companyAddr);
        dest.writeString(this.companyPhone);
        dest.writeString(this.job);
        dest.writeString(this.userType);
        dest.writeString(this.isValid);
        dest.writeString(this.createPersonId);
        dest.writeString(this.createTime);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.modifyTime);
        dest.writeString(this.remark);
        dest.writeString(this.empno);
        dest.writeString(this.dictId);
        dest.writeString(this.accredit);
        dest.writeString(this.birthday);
        dest.writeString(this.sex);
        dest.writeString(this.address);
        dest.writeString(this.petName);
        dest.writeString(this.homeTown);
        dest.writeString(this.dictName);
        dest.writeByte(this.friend ? (byte) 1 : (byte) 0);
        dest.writeString(this.hUsername);
        dest.writeString(this.hPassword);
        dest.writeString(this.sameLabels);
        dest.writeValue(this.sameCircleCounts);
        dest.writeString(this.balance);
        dest.writeString(this.city);
    }

    protected UserInfo(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.mobilePhoneKey = in.readString();
        this.phone = in.readString();
        this.bindMobileCount = in.readString();
        this.email = in.readString();
        this.qq = in.readString();
        this.wechat = in.readString();
        this.icon = in.readString();
        this.token = in.readString();
        this.referralCode = in.readString();
        this.myReferralCode = in.readString();
        this.companyName = in.readString();
        this.companyAddr = in.readString();
        this.companyPhone = in.readString();
        this.job = in.readString();
        this.userType = in.readString();
        this.isValid = in.readString();
        this.createPersonId = in.readString();
        this.createTime = in.readString();
        this.modifyPersonId = in.readString();
        this.modifyTime = in.readString();
        this.remark = in.readString();
        this.empno = in.readString();
        this.dictId = in.readString();
        this.accredit = in.readString();
        this.birthday = in.readString();
        this.sex = in.readString();
        this.address = in.readString();
        this.petName = in.readString();
        this.homeTown = in.readString();
        this.dictName = in.readString();
        this.friend = in.readByte() != 0;
        this.hUsername = in.readString();
        this.hPassword = in.readString();
        this.sameLabels = in.readString();
        this.sameCircleCounts = (Integer) in.readValue(Integer.class.getClassLoader());
        this.balance=in.readString();
        this.city=in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}

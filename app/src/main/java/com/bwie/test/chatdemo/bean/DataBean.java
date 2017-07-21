package com.bwie.test.chatdemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者： 武星宇
 * 日期： 2017/7/11.
 * 类用途：
 */
@Entity
public  class DataBean {
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getLat() {
    return this.lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public int getUserId() {
    return this.userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getImagePath() {
    return this.imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getIntroduce() {
    return this.introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public double getLng() {
    return this.lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public long getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(long createtime) {
    this.createtime = createtime;
  }

  public long getLasttime() {
    return this.lasttime;
  }

  public void setLasttime(long lasttime) {
    this.lasttime = lasttime;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getArea() {
    return this.area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  /**
   * area : 甘肃省-白银市-白银区
   * password : 80ac32fccaa9f0030bea18b8463d66b2
   * lasttime : 1499693968265
   * createtime : 1499691972764
   * gender : 男
   * lng : 116.300617
   * phone : 18811587426
   * introduce : 我是卡萨丁
   * imagePath : http://qhb.2dyt.com/MyInterface/images/efba9fd7-e45f-43c6-bb61-e2746aec7a3f.jpg
   * nickname : 奥里给
   * userId : 203
   * lat : 40.04027
   */
  @Id(autoincrement = true)
  private Long id;
  private String area;
  private String password;
  private long lasttime;
  private long createtime;
  private String gender;
  private double lng;
  private String phone;
  private String introduce;
  private String imagePath;
  private String nickname;
  private String yxpassword;

  public String getYxpassword() {
    return yxpassword;
  }

  public void setYxpassword(String yxpassword) {
    this.yxpassword = yxpassword;
  }

  private int userId;
  private double lat;
  @Generated(hash = 833383309)
  public DataBean(Long id, String area, String password, long lasttime, long createtime,
      String gender, double lng, String phone, String introduce, String imagePath, String nickname,
      String yxpassword, int userId, double lat) {
    this.id = id;
    this.area = area;
    this.password = password;
    this.lasttime = lasttime;
    this.createtime = createtime;
    this.gender = gender;
    this.lng = lng;
    this.phone = phone;
    this.introduce = introduce;
    this.imagePath = imagePath;
    this.nickname = nickname;
    this.yxpassword = yxpassword;
    this.userId = userId;
    this.lat = lat;
  }

  @Generated(hash = 908697775)
  public DataBean() {
  }


}

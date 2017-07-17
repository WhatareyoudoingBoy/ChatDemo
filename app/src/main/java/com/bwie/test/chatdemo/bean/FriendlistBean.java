package com.bwie.test.chatdemo.bean;

import java.util.List;

/**
 * 作者： 武星宇
 * 日期： 2017/7/14.
 * 类用途：
 */

public class FriendlistBean {

  /**
   * result_message : success
   * data : [{"area":"江苏省 常州市 天宁区","picWidth":720,"createtime":1500027295921,"picHeight":795,"gender":"男","lng":116.297487,"introduce":"世界一番強いです","imagePath":"http://qhb.2dyt.com/MyInterface/images/e6262791-c526-4a8c-b6cd-161641d9ca05.jpg","userId":3,"yxpassword":"NI79OMMU","relation":0,"password":"698d51a19d8a121ce581499d7b701668","lasttime":1500028180109,"phone":"18739282125","nickname":"ナルー","age":"20","lat":40.039738},{"area":"安徽\u2014淮北\u2014濉溪","picWidth":720,"createtime":1500027745806,"picHeight":720,"gender":"男","lng":116.293986,"introduce":"hhaha","imagePath":"http://qhb.2dyt.com/MyInterface/images/96c2cb7a-077e-423e-98e2-c264597f4a7e.jpg","userId":6,"yxpassword":"6Az1F3p6","relation":0,"password":"b59c67bf196a4758191e42f76670ceba","lasttime":1500029739323,"phone":"13645616851","nickname":"安徽","age":"18","lat":40.039216},{"area":"北京市 北京市 海淀区","picWidth":720,"createtime":1500027418185,"picHeight":720,"gender":"女","lng":116.293657,"introduce":"萌萌哒啊","imagePath":"http://qhb.2dyt.com/MyInterface/images/9d532cd4-5afd-4607-9198-c61652163c01.jpg","userId":4,"yxpassword":"657SO9ev","relation":0,"password":"e10adc3949ba59abbe56e057f20f883e","lasttime":1500028133718,"phone":"13111111111","nickname":"美少女啊","age":"22","lat":40.039172},{"area":"北京 东城区","picWidth":700,"createtime":1500028040999,"picHeight":742,"gender":"女","lng":0,"introduce":"123","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_woman.jpg","userId":8,"yxpassword":"F0mcwCVk","relation":0,"password":"202cb962ac59075b964b07152d234b70","lasttime":1500029927934,"phone":"18888888888","nickname":"zhaizhai","age":"24","lat":0}]
   * result_code : 200
   */

  private String result_message;
  private int result_code;
  private List< DataBean > data;

  public String getResult_message() {
    return result_message;
  }

  public void setResult_message(String result_message) {
    this.result_message = result_message;
  }

  public int getResult_code() {
    return result_code;
  }

  public void setResult_code(int result_code) {
    this.result_code = result_code;
  }

  public List< DataBean > getData() {
    return data;
  }

  public void setData(List< DataBean > data) {
    this.data = data;
  }

  public static class DataBean {
    /**
     * area : 江苏省 常州市 天宁区
     * picWidth : 720
     * createtime : 1500027295921
     * picHeight : 795
     * gender : 男
     * lng : 116.297487
     * introduce : 世界一番強いです
     * imagePath : http://qhb.2dyt.com/MyInterface/images/e6262791-c526-4a8c-b6cd-161641d9ca05.jpg
     * userId : 3
     * yxpassword : NI79OMMU
     * relation : 0
     * password : 698d51a19d8a121ce581499d7b701668
     * lasttime : 1500028180109
     * phone : 18739282125
     * nickname : ナルー
     * age : 20
     * lat : 40.039738
     */

    private String area;
    private int picWidth;
    private long createtime;
    private int picHeight;
    private String gender;
    private double lng;
    private String introduce;
    private String imagePath;
    private int userId;
    private String yxpassword;
    private int relation;
    private String password;
    private long lasttime;
    private String phone;
    private String nickname;
    private String age;
    private double lat;

    public String getArea() {
      return area;
    }

    public void setArea(String area) {
      this.area = area;
    }

    public int getPicWidth() {
      return picWidth;
    }

    public void setPicWidth(int picWidth) {
      this.picWidth = picWidth;
    }

    public long getCreatetime() {
      return createtime;
    }

    public void setCreatetime(long createtime) {
      this.createtime = createtime;
    }

    public int getPicHeight() {
      return picHeight;
    }

    public void setPicHeight(int picHeight) {
      this.picHeight = picHeight;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public double getLng() {
      return lng;
    }

    public void setLng(double lng) {
      this.lng = lng;
    }

    public String getIntroduce() {
      return introduce;
    }

    public void setIntroduce(String introduce) {
      this.introduce = introduce;
    }

    public String getImagePath() {
      return imagePath;
    }

    public void setImagePath(String imagePath) {
      this.imagePath = imagePath;
    }

    public int getUserId() {
      return userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    public String getYxpassword() {
      return yxpassword;
    }

    public void setYxpassword(String yxpassword) {
      this.yxpassword = yxpassword;
    }

    public int getRelation() {
      return relation;
    }

    public void setRelation(int relation) {
      this.relation = relation;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public long getLasttime() {
      return lasttime;
    }

    public void setLasttime(long lasttime) {
      this.lasttime = lasttime;
    }

    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public String getAge() {
      return age;
    }

    public void setAge(String age) {
      this.age = age;
    }

    public double getLat() {
      return lat;
    }

    public void setLat(double lat) {
      this.lat = lat;
    }
  }
}

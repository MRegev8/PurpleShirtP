package com.example.purpleshirt;

import java.util.*;

public class leadster {


    public String fullName;
    public String nickName;
    public String passWord;
    public String eshkol;
    public String city;
    public String phone;
    public int grade;
    public List<Boolean> attendance = new ArrayList<Boolean>();
    ;
    public int count;
    public String role;
    public Date bDay;
    public leadster(String fn,String pw, String e,String c,String p,int g,String d, String m, String y){
        this.fullName=fn;
        this.passWord=pw;
        this.eshkol=e;
        this.city=c;
        this.phone=p;
        this.grade=g;
        this.bDay=new Date(d,m,y);
        this.nickName=this.fullName;
        this.count=0;
        this.role="חבר הנהגה";
    }

    public leadster(String fn,String nn,String pw,String e,String c,String p,int g,String d, String m, String y){
        this.fullName=fn;
        this.passWord=pw;
        this.nickName=nn;
        this.eshkol=e;
        this.city=c;
        this.phone=p;
        this.grade=g;
        this.bDay=new Date(d,m,y);
        this.count=0;
        this.role="חבר הנהגה";
    }

    public leadster () {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEshkol() {
        return eshkol;
    }

    public void setEshkol(String eshkol) {
        this.eshkol = eshkol;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Boolean> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Boolean> attendance) {
        this.attendance = attendance;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getbDay() {
        return bDay;
    }

    public void setbDay(Date bDay) {
        this.bDay = bDay;
    }

    @Override
    public String toString() {
        return "שם מלא: " + fullName +
                "\nכינוי: " + nickName +
                "\nאשכול: " + eshkol +
                "\nעיר: " + city +
                "\nטלפון: " + phone +
                "\nשכבה: " + grade +
                "\nנכח ב" + count + " הנהגות" +
                "\nתפקיד בהנהגה: " + role +
                "\nיום הולדת: " + bDay.GetDate();
    }
}

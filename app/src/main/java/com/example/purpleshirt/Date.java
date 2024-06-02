package com.example.purpleshirt;

public class Date {
    String day;
    String month;
    String year;
    public Date(String d,String m,String y){
        this.day=d;
        this.month=m;
        this.year=y;
    }

    public Date () {
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
    private void ChangeDate(String d,String m,String y){
        this.day=d;
        this.month=m;
        this.year=y;
    }
    public String GetDate(){
        return String.valueOf(this.day)+"."+ String.valueOf(this.month)+"."+String.valueOf(this.year);
    }
}

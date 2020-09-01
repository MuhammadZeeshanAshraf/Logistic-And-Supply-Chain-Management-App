package com.example.logisticandsupplychainmanagementsystem.Models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {
    String ID;
    String balance;
    String canteenId;
    String Cost;
    String Description;
    String Email;
    String Enable;
    String FBID;
    String foodName;
    String orderBy;
    String Date;
    String Status;
    String Time;
    String Phone;
    String RollNo;
    String studentId;
    String deliveryTime;
    String Uri;

    public Order() {
    }

    public Order(String ID, String balance, String canteenId, String cost, String description, String email, String enable, String FBID, String foodName, String orderBy, String date, String status, String time, String phone, String rollNo, String studentId, String deliveryTime, String uri) {
        this.ID = ID;
        this.balance = balance;
        this.canteenId = canteenId;
        Cost = cost;
        Description = description;
        Email = email;
        Enable = enable;
        this.FBID = FBID;
        this.foodName = foodName;
        this.orderBy = orderBy;
        Date = date;
        Status = status;
        Time = time;
        Phone = phone;
        RollNo = rollNo;
        this.studentId = studentId;
        this.deliveryTime = deliveryTime;
        Uri = uri;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEnable() {
        return Enable;
    }

    public void setEnable(String enable) {
        Enable = enable;
    }

    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }
}

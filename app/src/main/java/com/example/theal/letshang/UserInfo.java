package com.example.theal.letshang;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by theal on 12/23/2017.
 */
@IgnoreExtraProperties
public class UserInfo {
    public String fullName;
    public String birthdate;
    public String phone;
    public Integer mostRecentSelection;
    public Integer activityCount;

    public UserInfo(String name, String birthdate, String phone, Integer mostRecentSelection, Integer activityCount) {
        this.fullName = name;
        this.birthdate = birthdate;
        this.phone = phone;
        this.mostRecentSelection = mostRecentSelection;
        this.activityCount = activityCount;
    }

    public UserInfo(){

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getMostRecentSelection() {
        return mostRecentSelection;
    }

    public void setMostRecentSelection(Integer mostRecentSelection) {
        this.mostRecentSelection = mostRecentSelection;
    }

    public Integer getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Integer activityCount) {
        this.activityCount = activityCount;
    }
}

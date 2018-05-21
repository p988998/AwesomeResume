package com.jiuzhang.guojing.awesomeresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Education implements Parcelable{

    public String id;

    public String school;

    public String major;

    public Date startDate;

    public Date endDate;

    public List<String> courses;

    public Education(){

    }
    protected Education (Parcel in){
        school = in.readString();
        major = in.readString();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        courses = in.createStringArrayList();

    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel parcel) {
            return new Education(parcel);
        }

        @Override
        public Education[] newArray(int i) {
            return new Education[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(school);
        parcel.writeString(major);
        parcel.writeLong(startDate.getTime());
        parcel.writeLong(endDate.getTime());
        parcel.writeStringList(courses);
    }
}

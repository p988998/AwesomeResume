package com.jiuzhang.guojing.awesomeresume.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class BasicInfo implements Parcelable{

    public String name;

    public String email;

    public Uri imageUri;

    //public String picturePath;

    public BasicInfo(){

    }

    protected BasicInfo(Parcel in){
        name = in.readString();
        email = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
        //imageUri = in.readString();

    }

    public static final Creator<BasicInfo> CREATOR = new Creator<BasicInfo>() {
        @Override
        public BasicInfo createFromParcel(Parcel parcel) {
            return new BasicInfo(parcel);
        }

        @Override
        public BasicInfo[] newArray(int i) {
            return new BasicInfo[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeParcelable(imageUri, i);
        //parcel.writeString(imageUri);

    }
}

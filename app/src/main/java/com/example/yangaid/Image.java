package com.example.yangaid;

public class Image {

    private String mName, mImageUrl;

    public Image() {

    }

    public Image(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        mImageUrl = mImageUrl;
    }
}

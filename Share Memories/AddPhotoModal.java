package com.tanyadas.wedmist.shareMemories;

public class AddPhotoModal {
    private int id;
    private String image;
    private String username;
    private String userNameContent;
    private String caption;
    private String hastag;

    public String getUserNameContent() {
        return userNameContent;
    }

    public void setUserNameContent(String userNameContent) {
        this.userNameContent = userNameContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getHastag() {
        return hastag;
    }

    public void setHastag(String hastag) {
        this.hastag = hastag;
    }
}

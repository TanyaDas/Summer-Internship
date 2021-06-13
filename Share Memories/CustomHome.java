package com.tanyadas.wedmist.shareMemories;

public class CustomHome {
    private String username, image, caption, hastag, userNameContent;

    public CustomHome(String username, String userNameContent, String image, String caption, String hastag) {
        this.username = username;
        this.userNameContent = userNameContent;
        this.image = image;
        this.caption = caption;
        this.hastag = hastag;
    }

    public String getUserNameContent() {
        return userNameContent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

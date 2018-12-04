package com.smart.tvpos.bean;

import java.util.Date;
import java.util.List;

public class PartyInfoEntity {

    private String status;
    private String name;
    private Date partyStart;
    private Date partyEnd;
    private String address;
    private int maxNum;
    private Date bookStart;
    private Date bookEnd;
    private String img;
    private String content;
    private List<String> images;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPartyStart() {
        return partyStart;
    }

    public void setPartyStart(Date partyStart) {
        this.partyStart = partyStart;
    }

    public Date getPartyEnd() {
        return partyEnd;
    }

    public void setPartyEnd(Date partyEnd) {
        this.partyEnd = partyEnd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Date getBookStart() {
        return bookStart;
    }

    public void setBookStart(Date bookStart) {
        this.bookStart = bookStart;
    }

    public Date getBookEnd() {
        return bookEnd;
    }

    public void setBookEnd(Date bookEnd) {
        this.bookEnd = bookEnd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

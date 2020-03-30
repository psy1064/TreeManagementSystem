package com.example.treemanagement;

import java.io.Serializable;

public class Tree implements Serializable {
    String name;            // 이름
    String category;        // 카테고리
    String keyword;         // 키워드
    int count;             // 보유수량
    int price;              // 주당가격
    String location;        // 위치
    String ect;             // 특이사항
    String treeHeight;      // 수고
    String rootCollar;      // 근원직경
    String breastHeight;    // 흉고직경
    String widthCrown;      // 수관폭
    String length;          // 수관길이
    String crownHeight;     // 지하고
    String manner;          // 육종방법
    String dateTime;        // 입력시간

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEct() {
        return ect;
    }

    public void setEct(String ect) {
        this.ect = ect;
    }

    public String getTreeHeight() {
        return treeHeight;
    }

    public void setTreeHeight(String treeHeight) {
        this.treeHeight = treeHeight;
    }

    public String getRootCollar() {
        return rootCollar;
    }

    public void setRootCollar(String rootCollar) {
        this.rootCollar = rootCollar;
    }

    public String getBreastHeight() {
        return breastHeight;
    }

    public void setBreastHeight(String breastHeight) {
        this.breastHeight = breastHeight;
    }

    public String getWidthCrown() {
        return widthCrown;
    }

    public void setWidthCrown(String widthCrown) {
        this.widthCrown = widthCrown;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCrownHeight() {
        return crownHeight;
    }

    public void setCrownHeight(String crownHeight) {
        this.crownHeight = crownHeight;
    }

    public String getManner() {
        return manner;
    }

    public void setManner(String manner) {
        this.manner = manner;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


}

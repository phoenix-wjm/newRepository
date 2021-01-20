package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import java.io.Serializable;

//分页
public class MyPage implements Serializable {
    //页码
    private int page=1;
    //每页显示的行数
    private int row=3;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}

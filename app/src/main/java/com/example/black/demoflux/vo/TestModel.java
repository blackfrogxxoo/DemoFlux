package com.example.black.demoflux.vo;

/**
 * Created by black on 2017/5/10.
 */

public class TestModel {
    private final int id;
    private String content;

    public TestModel(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

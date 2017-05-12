package com.example.black.demoflux.action;

import com.example.black.demoflux.core.IAction;

/**
 * Created by black on 2017/5/11.
 */

public class UpdateTestModelAction implements IAction {
    private final int id;
    private final String content;

    public UpdateTestModelAction(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

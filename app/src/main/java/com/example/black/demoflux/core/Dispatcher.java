package com.example.black.demoflux.core;

import com.example.black.demoflux.action.LoadMoreTestModelAction;
import com.example.black.demoflux.action.UpdateTestModelAction;
import com.example.black.demoflux.core.store.TestModelStore;

/**
 * Created by black on 2017/5/10.
 */

public enum Dispatcher {
    INSTANCE;
    public void postAction(IAction action) {
        if(action instanceof LoadMoreTestModelAction || action instanceof UpdateTestModelAction) {
            TestModelStore.getInstance().onAction(action);
        }
    }
}

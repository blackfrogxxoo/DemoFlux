package com.example.black.demoflux.core.store;

import com.example.black.demoflux.core.IAction;
import com.example.black.demoflux.action.LoadMoreTestModelAction;
import com.example.black.demoflux.action.UpdateTestModelAction;
import com.example.black.demoflux.vo.TestModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by black on 2017/5/10.
 */

public class TestModelStore {
    private static TestModelStore sInstance;
    public static final int PAGE_SIZE = 8;
    private List<TestModel> testModels = new ArrayList<>();
    private int currentId;
    private int mPage = 0;

    public static TestModelStore getInstance() {
        if(sInstance == null) {
            synchronized (TestModelStore.class) {
                if(sInstance == null) {
                    sInstance = new TestModelStore();
                }
            }
        }
        return sInstance;
    }

    private TestModelStore() {}

    private void updateModel(int id, String content) {
        Observable.just(0).subscribeOn(Schedulers.io())
                .doOnNext(i -> {
                    for(TestModel model : testModels) {
                        if(model.getId() == id) {
                            model.setContent(content);
                            break;
                        }
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    EventBus.getDefault().post(new EventItemChange(id, content));
                });
    }
    private void loadMore() {
        Observable.just(0).subscribeOn(Schedulers.io())
                .doOnNext(o -> {
                    mPage ++;
                    for(int i = 0; i < PAGE_SIZE; i++) {
                        testModels.add(new TestModel(currentId++, "Item " + currentId));
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    EventBus.getDefault().post(new EventListChange());
                });
    }


    public List<TestModel> getModelsByLastPage() {
        List<TestModel> result = new ArrayList<>();
        for(int i = 0; i < PAGE_SIZE; i++) {
            result.add(testModels.get(mPage * PAGE_SIZE - PAGE_SIZE + i));
        }
        return result;
    }

    public TestModel getModelById(int id) {
        for(TestModel item : testModels) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void onAction(IAction action) {
        if(action instanceof LoadMoreTestModelAction) {
            loadMore();
        } else if(action instanceof UpdateTestModelAction){
            updateModel(((UpdateTestModelAction) action).getId(), ((UpdateTestModelAction) action).getContent());
        }
    }



    /************************Change部分***************************/

    public static class EventItemChange {
        private final int id;
        private final String content;

        private EventItemChange(int id, String content) {
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


    public static class EventListChange {
        private EventListChange(){}
    }
}

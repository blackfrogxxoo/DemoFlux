package com.example.black.demoflux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.black.demoflux.action.LoadMoreTestModelAction;
import com.example.black.demoflux.action.UpdateTestModelAction;
import com.example.black.demoflux.core.Dispatcher;
import com.example.black.demoflux.core.store.TestModelStore;
import com.example.black.demoflux.core.store.TestModelStore.*;
import com.example.black.demoflux.view_binder.TestModelViewBinder;
import com.example.black.demoflux.vo.TestModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.rv_main) RecyclerView rvMain;
    @BindView(R2.id.btn_load_more) Button btnLoadMore;

    private MultiTypeAdapter mAdapter;
    private Items items = new Items();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        TestModelViewBinder.OnUpdateModelAction action = (id, content) -> Dispatcher.INSTANCE.postAction(new UpdateTestModelAction(id, content));
        btnLoadMore.setOnClickListener(v -> Dispatcher.INSTANCE.postAction(new LoadMoreTestModelAction()));
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MultiTypeAdapter();
        TestModelViewBinder binder = new TestModelViewBinder();
        binder.setOnUpdateModelAction(action);
        mAdapter.register(TestModel.class, binder);
        mAdapter.setItems(items);
        rvMain.setAdapter(mAdapter);
        Dispatcher.INSTANCE.postAction(new LoadMoreTestModelAction());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onListChanged(EventListChange ev) {
        items.addAll(TestModelStore.getInstance().getModelsByLastPage());
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onItemChanged(EventItemChange ev) {
        for(int i = 0; i < items.size(); i++) {
            TestModel model = (TestModel) items.get(i);
            if(model.getId() == ev.getId()) {
                model.setContent(ev.getContent());
                mAdapter.notifyItemChanged(i);
                break;
            }
        }
    }
}

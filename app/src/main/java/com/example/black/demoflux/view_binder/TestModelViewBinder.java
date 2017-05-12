package com.example.black.demoflux.view_binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.black.demoflux.R;
import com.example.black.demoflux.vo.TestModel;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by black on 2017/5/12.
 */

public class TestModelViewBinder extends ItemViewBinder<TestModel, TestModelViewBinder.ViewHolder> {
    private OnUpdateModelAction mOnUpdateModelAction;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_test_model, parent, false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TestModel item) {
        holder.tvContent.setText(item.getContent());
        holder.itemView.setOnClickListener(l -> {
            if(mOnUpdateModelAction != null) {
                mOnUpdateModelAction.onChange(item.getId(), "Changed");
            }
        });
    }

    public void setOnUpdateModelAction(OnUpdateModelAction onUpdateModelAction) {
        this.mOnUpdateModelAction = onUpdateModelAction;
    }

    public interface OnUpdateModelAction {
        void onChange(int id, String content);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        ViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}

package com.yb.peopleservice.view.weight.CustomPopup;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.lxj.xpopup.core.CenterPopupView;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
import com.yb.peopleservice.view.adapter.user.GroupAdapter;

import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/22 15:31
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class GroupPopup extends CenterPopupView {
    private Context context;
    private GroupAdapter adapter;
    private List<GroupBean> listData;

    public GroupPopup(@NonNull Context context, List<GroupBean> listData) {
        super(context);
        this.context = context;
        this.listData = listData;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_group;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.closeImg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 关闭弹窗
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setNewData(listData,true);
    }

    @Override
    protected int getMaxHeight() {
        return SizeUtils.dp2px(500);
    }

    @Override
    protected int getPopupHeight() {
        return SizeUtils.dp2px(500);
    }
}

package com.yb.peopleservice.view.fragment.user.order;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.yb.peopleservice.R;

import java.util.ArrayList;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/7 14:20
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class CouponDialogFragment extends BottomSheetDialogFragment {
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    private ArrayList<String> list = new ArrayList<>();
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    public CouponDialogFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    View parent = (View) view.getParent();
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                    CoordinatorLayout.Behavior behavior = params.getBehavior();
                    mBottomSheetBehavior = (BottomSheetBehavior<View>) behavior;
                    //设置高度
                    int height = ScreenUtils.getScreenHeight() / 2;
                    mBottomSheetBehavior.setPeekHeight(height);

                    parent.setBackgroundColor(Color.TRANSPARENT);
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon_dialog, container, false);
        initViews(view);
        return view;
    }


    private void initViews(View view) {
        for (int i = 0; i < 100; i++) {
            list.add("条目" + i);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_coupon) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setNewData(list);
    }

}

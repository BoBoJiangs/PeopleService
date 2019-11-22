package com.yb.peopleservice.view.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;

import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class HomePageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomePageAdapter() {
        super(R.layout.e_adapter_page_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.getView(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLong(helper.getAdapterPosition()+"");
            }
        });
    }
}

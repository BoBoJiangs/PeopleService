package com.yb.peopleservice.view.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.ClassifyListBean;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ClassifyAdapter extends BaseQuickAdapter<ClassifyListBean, BaseViewHolder> {

    private String name = "";

    public void setName(String name){
        this.name = name;
        notifyDataSetChanged();
    }

    public ClassifyAdapter() {
        super(R.layout.e_adapter_classify);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyListBean item) {
        TextView titleTV = helper.getView(R.id.titleTV);
        helper.setText(R.id.titleTV, "一级分类" + item.getName());
        helper.setVisible(R.id.lineTV, name.equals(item.getName()));
        if (name.equals(item.getName())) {
            titleTV.setTextColor(ContextCompat.getColor(mContext,R.color.base_text_color));
            titleTV.setTextSize(16);
        } else {
            titleTV.setTextColor(Color.BLACK);
            titleTV.setTextSize(12);
        }

    }
}

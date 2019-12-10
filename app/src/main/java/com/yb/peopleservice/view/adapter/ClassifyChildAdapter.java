package com.yb.peopleservice.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.ClassifyListBean;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ClassifyChildAdapter extends BaseQuickAdapter<ClassifyListBean, BaseViewHolder> {

    public ClassifyChildAdapter() {
        super(R.layout.e_adapter_classify_child);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyListBean item) {
        helper.setText(R.id.titleTV,item.getCategoryName());
    }
}

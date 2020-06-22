package com.yb.peopleservice.view.weight.CustomPopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.core.CenterPopupView;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.MessageBean;
import com.yb.peopleservice.model.presenter.message.MessageListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/22 15:31
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
@SuppressLint("ViewConstructor")
public class PushMessagePopup extends CenterPopupView {

    @BindView(R.id.titleUV)
    UtilityView titleUV;
    @BindView(R.id.contentUV)
    EditText contentUV;
    @BindView(R.id.yesBtn)
    Button yesBtn;
    private Context context;
    MessageListPresenter presenter;

    public PushMessagePopup(@NonNull Context context) {
        super(context);
        this.context = context;
        presenter = new MessageListPresenter(context,null,"1");
    }


    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.message_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this, getPopupImplView());
    }


    @OnClick(R.id.yesBtn)
    public void onViewClicked() {
        String title = titleUV.getContentText();
        if (StringUtils.isEmpty(title)){
            ToastUtils.showLong("请输入标题");
            return;
        }
        String content = contentUV.getText().toString();
        if (StringUtils.isEmpty(content)){
            ToastUtils.showLong("请输入内容");
            return;
        }
        MessageBean messageBean = new MessageBean();
        messageBean.setTitle(title);
        messageBean.setContent(content);
        presenter.addMessage(messageBean);
    }
}

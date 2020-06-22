package com.yb.peopleservice.model.presenter.chat;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.push.TagAliasOperatorHelper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

import static com.yb.peopleservice.push.TagAliasOperatorHelper.ACTION_SET;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/7 0:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ChatPresenter {

    private static volatile ChatPresenter instance;


    public static ChatPresenter getInstance() {
        if (instance == null) {
            synchronized (ChatPresenter.class) {
                if (instance == null) {
                    instance = new ChatPresenter();
                }

            }
        }
        return instance;
    }

    public void getUserInfo(String id, String nickName) {
        JMessageClient.getUserInfo(id, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (userInfo != null) {
                    loginPush(id);
                } else {
                    //去注册
                    registPush(id, nickName);
                }
            }
        });
    }

    /**
     * 注册IM
     * @param id
     * @param nickName
     */
    private void registPush(String id, String nickName) {
        RegisterOptionalUserInfo userInfo = new RegisterOptionalUserInfo();
        userInfo.setNickname(nickName);
        JMessageClient.register(id, AppConstant.CHAT_PASSWORD, userInfo, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0 || i == 898001) {
                    loginPush(id);
                    LogUtils.e("IM注册成功");
                } else {
                    LogUtils.e("IM注册失败:" + i);
                }
            }
        });
    }

    /**
     * 登录IM
     * @param userCode
     */
    private void loginPush(String userCode) {
        UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo != null) {
            return;
        }
        //检测账号是否登陆
        JMessageClient.login(userCode, AppConstant.CHAT_PASSWORD, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    LogUtils.e("登陆成功" + appKey);
                } else {
                    LogUtils.e("登陆失败" + responseMessage);
                }
            }
        });
    }

    /**
     * 更新IM用户名称和头像
     */
    public void updateUser(String name,String headUrl) {
        UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo != null && !StringUtils.isEmpty(name)) {
            myInfo.setNickname(name);
            JMessageClient.updateMyInfo(UserInfo.Field.nickname, myInfo, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    LogUtils.i("昵称信息：" + i + "==" + s);
                }
            });
        }
        if (!StringUtils.isEmpty(headUrl)) {
            JMessageClient.updateUserAvatar(new File(headUrl), new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    LogUtils.i("头像信息：" + i + "==" + s);
                }
            });
        }
    }

    /**
     * 设置用户推送签名
     * @param context
     * @param id
     */
    public void setCustomerAlias(Context context, String id) {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        LogUtils.e(id);
        tagAliasBean.alias = id;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, 1, tagAliasBean);

        Set<String> tags = new HashSet<>();
        tags.add("customer");

        TagAliasOperatorHelper.TagAliasBean tagBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        tagAliasBean.tags = tags;
        TagAliasOperatorHelper.getInstance().handleAction(context, 1, tagBean);
    }

    /**
     * 设置商户推送签名
     * @param context
     * @param id
     */
    public void setShopAlias(Context context,ServiceInfo data){
        TagAliasOperatorHelper.TagAliasBean aliasBean = new TagAliasOperatorHelper.TagAliasBean();
        aliasBean.action = ACTION_SET;
        LogUtils.e(data.getId().replace("-", ""));
        aliasBean.alias = data.getId().replace("-", "");
        aliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, 1, aliasBean);

        Set<String> tags = new HashSet<>();
        tags.add("shop");
        if (!StringUtils.isEmpty(data.getShopId())) {
            tags.add(data.getShopId().replace("-", ""));
        }
        TagAliasOperatorHelper.TagAliasBean tagsBean = new TagAliasOperatorHelper.TagAliasBean();
        tagsBean.action = ACTION_SET;
        tagsBean.tags = tags;
        TagAliasOperatorHelper.getInstance().handleAction(context, 1, tagsBean);
    }

    /**
     * 设置服务人员推送签名
     * @param context
     */
    public void setServiceAlias(Context context, ServiceInfo data){
        TagAliasOperatorHelper.TagAliasBean aliasBean = new TagAliasOperatorHelper.TagAliasBean();
        aliasBean.action = ACTION_SET;
        LogUtils.e(data.getId().replace("-", ""));
        aliasBean.alias = data.getId().replace("-", "");
        aliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, 1, aliasBean);

        Set<String> tags = new HashSet<>();
        tags.add("staff");
        if (!StringUtils.isEmpty(data.getShopId())) {
            tags.add(data.getShopId().replace("-", ""));
        }
        TagAliasOperatorHelper.TagAliasBean tagBean = new TagAliasOperatorHelper.TagAliasBean();
        tagBean.action = ACTION_SET;
        tagBean.tags = tags;
        TagAliasOperatorHelper.getInstance().handleAction(context, 1, tagBean);
    }
}

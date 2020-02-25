package com.yb.peopleservice.model.presenter.chat;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.utils.chat.SortConvList;
import com.yb.peopleservice.utils.chat.SortTopConvList;
import com.yb.peopleservice.view.adapter.chat.ConversationListAdapter;
import com.yb.peopleservice.view.fragment.chat.ConversationListFragment;
import com.yb.peopleservice.view.weight.chat.ConversationListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class ConversationListController implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ConversationListView mConvListView;
    private ConversationListFragment mContext;
    private int mWidth;
    private ConversationListAdapter mListAdapter;
    private List<Conversation> mDatas = new ArrayList<Conversation>();
    private Dialog mDialog;

    public ConversationListController(ConversationListView listView, ConversationListFragment context,
                                      int width) {
        this.mConvListView = listView;
        this.mContext = context;
        this.mWidth = width;
        initConvListAdapter();
    }

    List<Conversation> topConv = new ArrayList<>();
    List<Conversation> forCurrent = new ArrayList<>();
    List<Conversation> delFeedBack = new ArrayList<>();

    private void initConvListAdapter() {
        forCurrent.clear();
        topConv.clear();
        delFeedBack.clear();
        int i = 0;
        mDatas = JMessageClient.getConversationList();
        if (mDatas != null && mDatas.size() > 0) {
            mConvListView.setNullConversation(true);
            SortConvList sortConvList = new SortConvList();
            Collections.sort(mDatas, sortConvList);
            for (Conversation con : mDatas) {
                //如果会话有聊天室会话就把这会话删除
                if (con.getTargetId().equals("feedback_Android") || con.getType().equals(ConversationType.chatroom)) {
                    delFeedBack.add(con);
                }
                if (!TextUtils.isEmpty(con.getExtra())) {
                    forCurrent.add(con);
                }
            }
            topConv.addAll(forCurrent);
            mDatas.removeAll(forCurrent);
            mDatas.removeAll(delFeedBack);

        } else {
            mConvListView.setNullConversation(false);
        }
        if (topConv != null && topConv.size() > 0) {
            SortTopConvList top = new SortTopConvList();
            Collections.sort(topConv, top);
            for (Conversation conv : topConv) {
                mDatas.add(i, conv);
                i++;
            }
        }
        mListAdapter = new ConversationListAdapter(mContext.getActivity(), mDatas, mConvListView);
        mConvListView.setConvListAdapter(mListAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_group_btn:
                mContext.showPopWindow();
                break;
            case R.id.search_title:
//                Intent intent = new Intent();
//                intent.setClass(mContext.getActivity(), SearchContactsActivity.class);
//                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        //点击会话条目
//        Intent intent = new Intent();
//        if (position > 0) {
//            //这里-3是减掉添加的三个headView
//            Conversation conv = mDatas.get(position - 3);
//            intent.putExtra(AppConstant.CONV_TITLE, conv.getTitle());
//            //群聊
//            if (conv.getType() == ConversationType.group) {
//                if (mListAdapter.includeAtMsg(conv)) {
//                    intent.putExtra("atMsgId", mListAdapter.getAtMsgId(conv));
//                }
//
//                if (mListAdapter.includeAtAllMsg(conv)) {
//                    intent.putExtra("atAllMsgId", mListAdapter.getatAllMsgId(conv));
//                }
//                long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
//                intent.putExtra(AppConstant.GROUP_ID, groupId);
//                intent.putExtra(AppConstant.DRAFT, getAdapter().getDraft(conv.getId()));
//                intent.setClass(mContext.getActivity(), ChatActivity.class);
//                mContext.getActivity().startActivity(intent);
//                return;
//                //单聊
//            } else if (conv.getType() == ConversationType.single) {
//                String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
//                intent.putExtra(AppConstant.TARGET_ID, targetId);
//                intent.putExtra(AppConstant.TARGET_APP_KEY, conv.getTargetAppKey());
//                intent.putExtra(AppConstant.DRAFT, getAdapter().getDraft(conv.getId()));
//            }
//            intent.setClass(mContext.getActivity(), ChatActivity.class);
//            mContext.getContext().startActivity(intent);
//
//        }
    }

    public ConversationListAdapter getAdapter() {
        return mListAdapter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//        final Conversation conv = mDatas.get(position - 3);
//        if (conv != null) {
//            View.OnClickListener listener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (v.getId()) {
//                        //会话置顶
//                        case R.id.jmui_top_conv_ll:
//                            //已经置顶,去取消
//                            if (!TextUtils.isEmpty(conv.getExtra())) {
//                                mListAdapter.setCancelConvTop(conv);
//                                //没有置顶,去置顶
//                            } else {
//                                mListAdapter.setConvTop(conv);
//                            }
//                            mDialog.dismiss();
//                            break;
//                        //删除会话
//                        case R.id.jmui_delete_conv_ll:
//                            if (conv.getType() == ConversationType.group) {
//                                JMessageClient.deleteGroupConversation(((GroupInfo) conv.getTargetInfo()).getGroupID());
//                            } else {
//                                JMessageClient.deleteSingleConversation(((UserInfo) conv.getTargetInfo()).getUserName());
//                            }
//                            mDatas.remove(position - 3);
//                            if (mDatas.size() > 0) {
//                                mConvListView.setNullConversation(true);
//                            } else {
//                                mConvListView.setNullConversation(false);
//                            }
//                            mListAdapter.notifyDataSetChanged();
//                            mDialog.dismiss();
//                            break;
//                        default:
//                            break;
//                    }
//
//                }
//            };
//            mDialog = DialogCreator.createDelConversationDialog(mContext.getActivity(), listener, TextUtils.isEmpty(conv.getExtra()));
//            mDialog.show();
//            mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
//        }
        return true;
    }
//    public void delConversation() {
//        mDatas.remove(AppConstant.delConversation);
//    }
}
package com.yxw.cn.repairservice.activity;

import android.widget.TextView;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.entity.NoticeBean;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.MsgUtils;
import com.yxw.cn.repairservice.view.TitleBar;

import butterknife.BindView;

/**
 * 查看图片
 */
public class MsgDetailActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_msg_detail;
    }

    @Override
    public void initView() {
        super.initView();
        titleBar.setTitle("消息详情");
        NoticeBean noticeBean = (NoticeBean) getIntent().getSerializableExtra("data");
        MsgUtils.deleteMsg(noticeBean.getNoticeId());
        EventBusUtil.post(MessageConstant.GET_MSG_COUNT);
        mTvTitle.setText(noticeBean.getTitle());
        mTvTime.setText(noticeBean.getCreateTime());
        mTvContent.setText(noticeBean.getContent());
    }

}
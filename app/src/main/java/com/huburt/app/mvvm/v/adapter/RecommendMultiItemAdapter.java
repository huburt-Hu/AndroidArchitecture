package com.huburt.app.mvvm.v.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huburt.app.R;
import com.huburt.app.entity.IndexData;
import com.huburt.app.entity.RecommendMultiItem;
import com.huburt.app.util.ConstantUtil;
import com.huburt.app.util.TextHandleUtil;
import com.huburt.architecture.imageloader.ImageLoaderManager;
import com.huburt.architecture.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class RecommendMultiItemAdapter extends BaseMultiItemQuickAdapter<RecommendMultiItem, BaseViewHolder> {

    private BGABanner.Adapter<ImageView, String> mBannerAdapter = new BGABanner.Adapter<ImageView, String>() {
        @Override
        public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
            ImageLoaderManager.getInstance().load(itemView, model);
        }
    };

    public RecommendMultiItemAdapter(List<RecommendMultiItem> data) {
        super(data);
        addItemType(RecommendMultiItem.BANNER, R.layout.item_banner_recommend);
        addItemType(RecommendMultiItem.STREAMER, R.layout.item_streamer_recommend);
        addItemType(RecommendMultiItem.ITEM_AV, R.layout.item_item_av_recommend);
        addItemType(RecommendMultiItem.ITEM_AV_RCMD_REASON, R.layout.item_item_av_rcmd_reason_recommend);
        addItemType(RecommendMultiItem.ITEM_BANGUMI, R.layout.item_item_bangumi_recommend);
        addItemType(RecommendMultiItem.ITEM_LOGIN, R.layout.item_item_login_recommend);
        addItemType(RecommendMultiItem.ITEM_AD_WEB_S, R.layout.item_item_ad_web_s_recommend);
        addItemType(RecommendMultiItem.ITEM_ARTICLE_S, R.layout.item_item_article_s_recommend);
        addItemType(RecommendMultiItem.PRE_HERE_CLICK_TO_REFRESH, R.layout.item_pre_here_click_to_refresh_recommend);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendMultiItem item) {
        IndexData.DataBean itemBean = item.getIndexDataBean();
        switch (item.getItemType()) {
            case RecommendMultiItem.BANNER:
                BGABanner banner = helper.getView(R.id.banner);
                List<IndexData.DataBean.BannerItemBean> banners = itemBean.getBanner_item();
                List<String> images = new ArrayList<>();
                if (banners != null && banners.size() > 0) {
                    for (int i = 0; i < banners.size(); i++) {
                        IndexData.DataBean.BannerItemBean bannerItem = banners.get(i);
                        images.add(bannerItem.getImage());
                    }
                }
                banner.setAdapter(mBannerAdapter);
                banner.setData(images, images);
                break;
            case RecommendMultiItem.STREAMER:
                ImageLoaderManager.getInstance().load(helper.getView(R.id.iv_streamer), itemBean.getCover());
                helper.setText(R.id.tv_title, itemBean.getTitle())
                        .setText(R.id.tv_badge, TextUtils.isEmpty(itemBean.getBadge()) ? ("topic".equals(itemBean.getGotoX()) ? "话题" : "") : itemBean.getBadge())
                        .setText(R.id.tv_desc, itemBean.getDesc());
                break;
            case RecommendMultiItem.ITEM_AV:
                setItemPaddingAndImage(helper, item, itemBean);
                helper.setText(R.id.tv_play, TextHandleUtil.handleCount2TenThousand(itemBean.getPlay()))
                        .setText(R.id.tv_danmaku, TextHandleUtil.handleCount2TenThousand(itemBean.getDanmaku()))
                        .setText(R.id.tv_duration, TextHandleUtil.handleDurationSecond(itemBean.getDuration()))
                        .setText(R.id.tv_title, itemBean.getTitle())
                        .setText(R.id.tv_tname_tag_name, itemBean.getTname() + "‧" + (itemBean.getTag() == null ? "" : itemBean.getTag().getTag_name()));
                break;
            case RecommendMultiItem.ITEM_AV_RCMD_REASON:
                setItemPaddingAndImage(helper, item, itemBean);
                helper.setText(R.id.tv_play, TextHandleUtil.handleCount2TenThousand(itemBean.getPlay()))
                        .setText(R.id.tv_danmaku, TextHandleUtil.handleCount2TenThousand(itemBean.getDanmaku()))
                        .setText(R.id.tv_duration, TextHandleUtil.handleDurationSecond(itemBean.getDuration()))
                        .setText(R.id.tv_title, itemBean.getTitle())
                        .setText(R.id.tv_rcmd_reason, itemBean.getRcmd_reason().getContent());
                break;
            case RecommendMultiItem.ITEM_BANGUMI:
                setItemPaddingAndImage(helper, item, itemBean);
                helper.setText(R.id.tv_play, TextHandleUtil.handleCount2TenThousand(itemBean.getPlay()))
                        .setText(R.id.tv_favorite, itemBean.getFavorite() + "")
                        .setText(R.id.tv_title, itemBean.getTitle())
                        .setText(R.id.tv_last_index, mContext.getResources().getString(R.string.recommend_home_update_to_last_index, itemBean.getLast_index()))
                        .setText(R.id.tv_badge, itemBean.getBadge());
                break;
            case RecommendMultiItem.ITEM_LOGIN:
                break;
            case RecommendMultiItem.ITEM_AD_WEB_S:
                setItemPaddingAndImage(helper, item, itemBean);
                helper.setText(R.id.tv_title, itemBean.getTitle())
                        .setText(R.id.tv_desc, itemBean.getDesc());
                break;
            case RecommendMultiItem.ITEM_ARTICLE_S:
                setItemPaddingAndImage(helper, item, itemBean);
                helper.setText(R.id.tv_play, TextHandleUtil.handleCount2TenThousand(itemBean.getPlay()))
                        .setText(R.id.tv_favorite, itemBean.getFavorite() + "")
                        .setText(R.id.tv_title, itemBean.getTitle())
                        .setText(R.id.tv_desc, itemBean.getDesc());
                break;
            case RecommendMultiItem.PRE_HERE_CLICK_TO_REFRESH:
                break;
        }
    }

    private void setItemPaddingAndImage(BaseViewHolder helper, RecommendMultiItem item, IndexData.DataBean itemBean) {
        int leftPadding = item.isOdd() ? DensityUtils.dip2px(mContext, ConstantUtil.MAIN_HOME_ITEM_PADDING) : 0;
        int rightPadding = item.isOdd() ? 0 : DensityUtils.dip2px(mContext, ConstantUtil.MAIN_HOME_ITEM_PADDING);
        helper.getView(R.id.fl_item).setPadding(leftPadding, 0, rightPadding, 0);
        ImageLoaderManager.getInstance().load(helper.getView(R.id.iv_cover), itemBean.getCover());
    }
}
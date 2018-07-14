package com.example.pengganggui.lvrtest2.module_essay.ui;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.pengganggui.lvrtest2.R;
import com.example.pengganggui.lvrtest2.module_essay.model.IEssayItem;

import java.util.List;

/**
 * Created by pengganggui on 2018/7/14.
 * 文章列表Adapter
 */

public class EssayListAdapter extends BaseMultiItemQuickAdapter<EssayListAdapter.MultiItem,BaseViewHolder> {

    private Context mContext;

    public EssayListAdapter(Context ctx,List<MultiItem> data) {
        super(data);
        this.mContext=ctx;
        int tem= R.layout.product_item;
        Log.e("changxing","id:"+tem);
        addItemType(MultiItem.TYPE_BASE,tem);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItem item) {
        item.update(mContext,helper);
    }

    public static class MultiItem implements MultiItemEntity{

        public final static int TYPE_BASE=1;
        public IEssayItem data;
        public int type;

        public void update(Context ctx,final BaseViewHolder helper){
            switch (type){
                case TYPE_BASE:
                    helper.setText(R.id.item_title,data.getTitle());
                    helper.setText(R.id.item_time, data.getDate());
                    helper.setText(R.id.item_from, data.getAuthor());

                    Glide.with(ctx)
                            .load(data.getImageUrl())
                            .centerCrop()
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    helper.setImageDrawable(R.id.icon_item, resource);
                                }
                            });
                    break;
            }
        }

        public MultiItem(IEssayItem d,int type) {
            data=d;
            this.type=type;
        }

        @Override
        public int getItemType() {
            return type;
        }
    }
}

package com.example.pengganggui.lvrtest2.module_essay.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;

/**
 * Created by pengganggui on 2018/7/14.
 * 自定义RecycleView的分割线
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration{

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight=2;//分割线高度
    private int mOrientation;//列表方向
    private static final int[] ATTRS=new int[]{android.R.attr.listDivider};

    public DividerItemDecoration(Context context,int mOrientation){
        if (mOrientation!= LinearLayoutManager.VERTICAL&&mOrientation!=LinearLayoutManager.HORIZONTAL){
            throw new IllegalStateException("请输入正确的方向");
        }
        this.mOrientation=mOrientation;
        final TypedArray array=context.obtainStyledAttributes(ATTRS);
        mDivider= array.getDrawable(0);
        array.recycle();
    }

    /**
     * 自定义分割线
     * @param context
     * @param mOrientation 列表方向
     * @param drawableId 分割线图片
     */
    public DividerItemDecoration(Context context,int mOrientation,int drawableId){
        this(context,mOrientation);
        mDivider= ContextCompat.getDrawable(context,drawableId);
        mDividerHeight=mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     * @param context
     * @param mOrientation 列表方向
     * @param mDividerHeight 分割线高度
     * @param dividerColor 分割线颜色
     */
    public DividerItemDecoration(Context context,int mOrientation,int mDividerHeight,int dividerColor){
        this(context,mOrientation);
        this.mDividerHeight=mDividerHeight;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 获取分割线尺寸
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,mDividerHeight);
    }

    /**
     * 绘制分割线
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation==LinearLayoutManager.VERTICAL){
            drawVertical(c,parent);
        }else {
            drawHorizontal(c,parent);
        }
    }

    /**
     * 绘制横向分割线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int left=parent.getPaddingLeft()+(int)dp2px(parent.getContext(),16);
        final int right=parent.getMeasuredWidth()-parent.getPaddingRight()-(int)dp2px(parent.getContext(),16);
        final int childSize=parent.getChildCount();
        for (int i=0;i<childSize;i++){
            final View child=parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams=(RecyclerView.LayoutParams)child.getLayoutParams();
            final int top=child.getBottom()+layoutParams.bottomMargin;
            final int bottom=top+mDividerHeight;
            if (mDivider!=null){
                mDivider.setBounds(left,top,right,bottom);
                mDivider.draw(c);
            }
            if (mPaint!=null){
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }


    public static float dp2px(Context cxt, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                cxt.getResources().getDisplayMetrics());
    }

    /**
     * 绘制纵向item分割线
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (mPaint != null) {
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}

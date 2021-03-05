package com.peng.wechatmoment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/12/1 5:53
 * desc   : 自适应高度的 GridView
 */
public class AdapterSelfGridView extends GridView {
    public AdapterSelfGridView(Context context) {
        super(context);
    }

    public AdapterSelfGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdapterSelfGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
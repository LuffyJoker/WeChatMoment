package com.peng.wechatmoment.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.peng.wechatmoment.R;

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/30 2:21
 * desc   :
 */
public class LoadingLayout extends FrameLayout {

    private int emptyViewLayoutId, errorViewLayoutId, loadingViewLayoutId;

    /**
     * 页面的提示信息
     */
    private String emptyContentDes;
    private String errorContentDes;

    /**
     * 操作按钮的内容
     */
    private String btnContent;

    /**
     * 空页面的刷新按钮是否显示
     */
    private boolean isShowRefreshBtnOfEmpty;
    /**
     * 网络错误页面的刷新按钮是否显示
     */
    private boolean isShowRefreshBtnOfError;

    private View emptyView, errorView, loadingView;
    private OnClickListener onLoadingClickListener;
    private Animation rightCircle;    //向右旋转的360度的动画

    private TextView btnNetErrorRetry;

    private Button btnNoDataRetry;

    private TextView tvEmptyDes;

    private TextView tvErrorDes;


    public LoadingLayout(Context context) {
        this(context, null);
    }


    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        try {

            emptyViewLayoutId = a.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.include_no_data); //空数据页面
            errorViewLayoutId = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.include_net_error);//网络失败页面
            loadingViewLayoutId = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.include_loading);//加载页面

            emptyContentDes = a.getString(R.styleable.LoadingLayout_emptyContentDes);// 空页面的描述内容
            errorContentDes = a.getString(R.styleable.LoadingLayout_errorContentDes);// 网络错误的描述内容

            btnContent = a.getString(R.styleable.LoadingLayout_btnContent);// 错误描述的内容

            isShowRefreshBtnOfEmpty = a.getBoolean(R.styleable.LoadingLayout_isShowRefreshBtnOfEmpty, true);// 错误描述的内容
            isShowRefreshBtnOfError = a.getBoolean(R.styleable.LoadingLayout_isShowRefreshBtnOfError, true);// 错误描述的内容

            loadingViewLayoutId = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.include_loading);//加载页面
            LayoutInflater inflater = LayoutInflater.from(getContext());
            emptyView = inflater.inflate(emptyViewLayoutId, this, true);
            errorView = inflater.inflate(errorViewLayoutId, this, true);
            loadingView = inflater.inflate(loadingViewLayoutId, this, true);
            initViews();

        } finally {
            a.recycle();
        }

    }

    //初始化视图
    private void initViews() {
        rightCircle = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rightCircle.setDuration(500);
        rightCircle.setFillAfter(true);

        btnNetErrorRetry = findViewById(R.id.tv_refresh);
        if (isShowRefreshBtnOfError) {
            btnNetErrorRetry.setVisibility(View.VISIBLE);
        } else {
            btnNetErrorRetry.setVisibility(View.GONE);
        }

        btnNoDataRetry = findViewById(R.id.tv_add_printer);
        if (isShowRefreshBtnOfEmpty) {
            btnNoDataRetry.setVisibility(View.VISIBLE);
        } else {
            btnNoDataRetry.setVisibility(View.GONE);
        }

        btnNoDataRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLoadingClickListener != null) {
                    onLoadingClickListener.onClick(v);
                }
            }
        });

        btnNetErrorRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingClickListener.onClick(v);
            }
        });

        tvEmptyDes = findViewById(R.id.tv_on_printer_tips);
        tvEmptyDes.setText(emptyContentDes);


        tvErrorDes = findViewById(R.id.tv_net_error_tip);
        tvErrorDes.setText(errorContentDes);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
        }

        btnNetErrorRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLoadingClickListener != null) {
                    onLoadingClickListener.onClick(v);
                }
            }
        });
    }

    public void setOnRetryClickListener(OnClickListener onLoadingClickListener) {
        this.onLoadingClickListener = onLoadingClickListener;
    }


    //显示空数据页面
    public void showEmpty() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    //显示网络失败页面
    public void showError() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    //显示加载Loading页面
    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    //显示想要加载的内容
    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 3) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }
}

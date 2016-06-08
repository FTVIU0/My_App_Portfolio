package com.nlte.myappportfolio.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.nlte.myappportfolio.R;

/**
 * NLTE
 * 2016/6/8 0008 0:45
 */
public class RefreshGridView extends GridView {
    public RefreshGridView(Context context) {
        this(context, null);
    }

    public RefreshGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    /**
     * 初始化脚部布局
     */
    private void initHeaderView() {
        View headerView = View.inflate(getContext(), R.layout.refresh_header, null);
    }
}

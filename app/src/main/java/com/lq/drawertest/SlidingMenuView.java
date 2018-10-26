package com.lq.drawertest;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenuView extends HorizontalScrollView {
    private static final String TAG = "SlidingMenuView";
    //包裹的唯一子view
    private LinearLayout mWrapper;
    //左侧菜单栏
    private ViewGroup mMenu;
    //右侧内容区域
    private ViewGroup mContent;
    //屏幕宽度单位px
    private int mScreenWidth;
    //屏幕与右侧的距离  单位px  ，默认为50dp
    private int mMenuRightPadding = 50;
    //只让onMeasure调用一次
    private boolean once = false;
    //menu的宽度
    private int mMenuWidth;
    //切换菜单隐藏与显示
    private boolean isOpen;

    /**
     * 当未自定义属性时 系统将调用该构造方法
     *
     * @param context
     * @param attrs
     */
    public SlidingMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlidingMenuView(Context context) {
        this(context, null);
    }

    /**
     * 自定义且使用了自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SlidingMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义的属性
        //获取自定义的属性
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenuView, defStyleAttr, 0);

        int n = ta.getIndexCount();
        for (int i = 0; i < n; ++i) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenuView_menuRightPadding:
                    //将默认的50dp转化为px
                    mMenuRightPadding = (int) ta.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 50, context.getResources().getDisplayMetrics()));

                    break;
            }
        }

        ta.recycle();

        //获取屏幕宽度
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

    }


    /**
     * 设置子View的宽高、设置自己的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!once) {
            //获取HorizontalScrollView中的为一个元素
            mWrapper = (LinearLayout) getChildAt(0);
            //获取菜单
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            //获取内容
            mContent = (ViewGroup) mWrapper.getChildAt(1);

            //设置菜单的宽度为屏幕的宽度-右边距
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;

            mContent.getLayoutParams().width = mScreenWidth;
            once = true;

        }


    }

    /**
     * 设置SlidingMenuView的位置
     * 应当将Content内容显示，将Menu隐藏在左侧 需设置偏移量实现
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //x为正值时滚动条向右移动 内容向左移动
            this.scrollTo(mMenuWidth, 0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                //这时应将菜单隐藏
                if (scrollX >= (mMenuWidth / 2)) {
                    this.smoothScrollTo(mMenuWidth, 0);
                } else {
                    smoothScrollTo(0, 0);
                }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(final int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l * 1.0f / mMenuWidth; // 1.0 ~ 0.0
        float rightScale = 0.7f + 0.3f * scale; // 1.0 ~ 0.7
        float leftScale = 1.0f - scale * 0.3f;  //0.7 ~ 1.0
        float leftAlpha = 0.6f + 0.4f * (1 - scale); // 0.6 ~ 1.0
        mMenu.setScaleX(leftScale);
        mMenu.setScaleY(leftScale);
        mMenu.setAlpha(leftAlpha);

        mContent.setPivotX(0);
        mContent.setPivotY(mContent.getHeight() / 2);
        mContent.setScaleX(rightScale);
        mContent.setScaleY(rightScale);

        mContent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (l == 0) {
                    SlidingMenuView.this.smoothScrollTo(mMenuWidth, 0);
                }

            }
        });


//        mMenu.setTranslationX((float) 1);
    }


}

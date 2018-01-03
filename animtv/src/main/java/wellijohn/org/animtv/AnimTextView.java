package wellijohn.org.animtv;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;

/**
 * @author: JiangWeiwei
 * @time: 2018/1/2-16:54
 * @email: wellijohn1991@gmail.com
 * @desc:
 */
public class AnimTextView<T> extends View {

    /**
     * 可自定义的属性
     */
    private final int mTextColor;
    private final int mTextSize;
    private final int mDuration;


    private DecimalFormat df = new DecimalFormat("######0.00");
    /**
     * 绘制的text
     */
    private T mDrawText;
    /**
     * 最终显示的text
     */
    private T mEndText;

    private Paint mTextPaint;

    /**
     * 需要显示的文本的长宽
     */
    private float mTextWidth;
    private float mTextHeight;
    /**
     * 是否是整数的数值
     */
    private boolean mIsInteger;

    public AnimTextView(Context context) {
        this(context, null);
    }

    public AnimTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnimTextView);
        mTextColor = ta.getColor(R.styleable.AnimTextView_textColor, Color.GRAY);
        mTextSize = ta.getDimensionPixelSize(R.styleable.AnimTextView_textSize, UiUtils.dip2px(context, 14));
        mDuration = ta.getInteger(R.styleable.AnimTextView_duration, 4000);
        ta.recycle();
        initPaint();

    }

    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
    }


    /**
     * @param paramEndText 整型和小数显示，小数只支持到小数点后两个位，如果有小数点后有3位以上，自动四舍五入
     */
    public void setText(T paramEndText) {
        this.mEndText = paramEndText;
        mTextWidth = UiUtils.getTextWidth(String.valueOf(mEndText), mTextPaint);
        mTextHeight = UiUtils.getTextHeight(String.valueOf(mEndText), mTextPaint);

        mIsInteger = paramEndText instanceof Integer;

        startAnim();
    }


    private void startAnim() {
        ValueAnimator va;
        if (mIsInteger) {
            va = ValueAnimator.ofInt(0, (Integer) mEndText);
        } else {
            va = ValueAnimator.ofFloat(0, Float.parseFloat(String.valueOf(mEndText)));
        }

        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawText = mIsInteger ? (T) animation.getAnimatedValue() : (T) df.format(animation.getAnimatedValue());
                ViewCompat.postInvalidateOnAnimation(AnimTextView.this);
            }
        });
        va.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(String.valueOf(mDrawText), getPaddingLeft(), getPaddingRight() + mTextHeight, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthParentMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthParentMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightParentMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightParentMeasureSize = MeasureSpec.getSize(heightMeasureSpec);


        ViewGroup.LayoutParams thisLp = getLayoutParams();

        int resultWidthSize = 0;
        int resultHeightSize = 0;
        int resultWidthMode = MeasureSpec.EXACTLY;//用来对childView进行计算的
        int resultHeightMode = MeasureSpec.EXACTLY;

        int paddingWidth = getPaddingLeft() + getPaddingRight();
        int paddingHeight = getPaddingTop() + getPaddingBottom();
        switch (widthParentMeasureMode) {
            //父view限制最大值
            case MeasureSpec.AT_MOST:
                //这个代表在布局写死了宽度
                if (thisLp.width > 0) {
                    resultWidthSize = thisLp.width > widthParentMeasureSize ? widthParentMeasureSize : thisLp.width;
                    resultWidthMode = MeasureSpec.EXACTLY;
                } else if (thisLp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    resultWidthSize = Math.max(0, widthParentMeasureSize - paddingWidth);
                    resultWidthMode = MeasureSpec.AT_MOST;
                } else if (thisLp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    resultWidthSize = (int) Math.min(mTextWidth + paddingWidth, widthParentMeasureSize - paddingWidth);
                    resultWidthMode = MeasureSpec.AT_MOST;
                }
                break;
            case MeasureSpec.EXACTLY:
                //这个代表在布局写死了宽度
                if (thisLp.width > 0) {
                    resultWidthSize = thisLp.width;
                    resultWidthMode = MeasureSpec.EXACTLY;
                } else if (thisLp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    resultWidthSize = Math.max(0, widthParentMeasureSize - paddingWidth);
                    resultWidthMode = MeasureSpec.EXACTLY;
                } else if (thisLp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    resultWidthSize = (int) Math.min(mTextWidth + paddingWidth, widthParentMeasureSize - paddingWidth);
                    resultWidthMode = MeasureSpec.AT_MOST;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                //这个代表在布局写死了宽度
                if (thisLp.width > 0) {
                    resultWidthSize = thisLp.width;
                    resultWidthMode = MeasureSpec.EXACTLY;
                } else if (thisLp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    resultWidthSize = 0;
                    resultWidthMode = MeasureSpec.UNSPECIFIED;
                } else if (thisLp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    resultWidthSize = 0;
                    resultWidthMode = MeasureSpec.UNSPECIFIED;
                }
                break;
        }

        switch (heightParentMeasureMode) {

            case MeasureSpec.AT_MOST:
                //这个代表在布局写死了宽度
                if (thisLp.height > 0) {
                    resultHeightSize = thisLp.height > heightParentMeasureSize ? heightParentMeasureSize : thisLp.height;
                    resultWidthMode = MeasureSpec.EXACTLY;
                } else if (thisLp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    resultHeightSize = Math.max(0, heightParentMeasureSize - paddingHeight);
                    resultHeightMode = MeasureSpec.AT_MOST;
                } else if (thisLp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    resultHeightSize = (int) Math.min(mTextHeight + paddingHeight, heightParentMeasureSize);
                    resultHeightMode = MeasureSpec.AT_MOST;
                }
                break;
            case MeasureSpec.EXACTLY:
                //这个代表在布局写死了宽度
                if (thisLp.height > 0) {
                    resultHeightSize = thisLp.height;
                    resultWidthMode = MeasureSpec.EXACTLY;
                } else if (thisLp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    resultHeightSize = Math.max(0, heightParentMeasureSize - paddingHeight);
                    resultHeightMode = MeasureSpec.EXACTLY;
                } else if (thisLp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    resultHeightSize = (int) Math.min(mTextHeight + paddingHeight, heightParentMeasureSize - paddingHeight);
                    resultHeightMode = MeasureSpec.AT_MOST;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                //这个代表在布局写死了宽度
                if (thisLp.height > 0) {
                    resultHeightSize = thisLp.height;
                    resultWidthMode = MeasureSpec.EXACTLY;
                } else if (thisLp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    resultHeightSize = 0;
                    resultHeightMode = MeasureSpec.UNSPECIFIED;
                } else if (thisLp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    resultHeightSize = 0;
                    resultHeightMode = MeasureSpec.UNSPECIFIED;
                }
                break;
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(resultWidthSize, resultWidthMode),
                MeasureSpec.makeMeasureSpec(resultHeightSize, resultHeightMode));
    }
}


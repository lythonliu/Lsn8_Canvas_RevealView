package com.example.myui.v07;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PathAnimView extends View {
    public PathAnimView(Context context) {
        this(context,null);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 由于园和直线路径不连续，于是开了两个path和两个动画分别控制
     */
    private Paint mPaint;
    private Path mPath,mCirclePath;
    private Path mRealPath,mRealCirclePath;
    private PathMeasure mPathMeasure,mCircleMeasure;
    private ValueAnimator animator,circleAnimator;
    private float animatorValue = 1,circleAnimValue = 0;
    private int animDuration = 500;
    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#123456"));
        mPaint.setStrokeWidth(8);
        mRealPath = new Path();
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(200,200);
        mPath.lineTo(400,200);
        mPath.lineTo(400-30,200-30);//保证斜率一样
        mPathMeasure = new PathMeasure(mPath,false);

        //画圆
        mCirclePath = new Path();
        mRealCirclePath = new Path();
        int radius = 30;//半径
        //圆心
        float x = (float) (400-30-Math.sqrt(2)*radius/2);
        float y = (float) (200-30-Math.sqrt(2)*radius/2);
        mCirclePath.addCircle(x,y,radius, Path.Direction.CCW);
        mCircleMeasure = new PathMeasure(mCirclePath,false);

        animator = ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(animDuration);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circleAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        circleAnimator = ValueAnimator.ofFloat(0,1);
        circleAnimator.setDuration(animDuration);
        circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAnimValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        circleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animator.start();
                circleAnimValue = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRealPath.reset();
        mRealPath.lineTo(0,0);
        mPathMeasure.getSegment(0,animatorValue*mPathMeasure.getLength(),mRealPath,true);
        canvas.drawPath(mRealPath,mPaint);
        mRealCirclePath.reset();
        mRealCirclePath.lineTo(0,0);
        mCircleMeasure.getSegment(0,circleAnimValue*mPathMeasure.getLength(),mRealCirclePath,true);
        canvas.drawPath(mRealCirclePath,mPaint);
        Log.d("onDraw",animatorValue*mPathMeasure.getLength()+"");
    }
}

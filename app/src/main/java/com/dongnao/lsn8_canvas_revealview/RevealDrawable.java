package com.dongnao.lsn8_canvas_revealview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

/**
 * Created by John on 2017/5/19.
 */

public class RevealDrawable extends Drawable{
    //灰色图
    private Drawable mUnselectedDrawable;
    // 彩色图
    private Drawable mSelectedDrawable;

    private Rect mTmpRect = new Rect();

    public RevealDrawable(Drawable unSelected, Drawable selected) {
        mUnselectedDrawable = unSelected;
        mSelectedDrawable = selected;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        // 将两张图片进行剪裁和拼接
        int level =  getLevel();
        if(level == 0 || level == 10000){
            // 画整张灰色的图
            mUnselectedDrawable.draw(canvas);
        }else if(level == 5000){
            // 画整张彩色的图
            mSelectedDrawable.draw(canvas);
        }else{

            Rect bounds = getBounds();
            Rect r = mTmpRect;
            // 比例 -1 ~ 1 之间进行变化 -1 ~0 表示左边灰色，右边彩色
            //                          0 ~1 表示左边是彩色，右边灰色
            float ratio = (level / 5000f) - 1f;

            // 1、画灰色区域
            {
                int w = bounds.width();
                int h = bounds.height();
                int gravity = ratio < 0 ? Gravity.LEFT : Gravity.RIGHT;
                w = (int) (w*Math.abs(ratio));
                Gravity.apply(
                        gravity, // 从哪个方向开始剪，左边还是右边
                        w,    // 目标矩形的宽
                        h,      // 目标矩形的高
                        bounds, // 被剪裁图片的rect
                        r       // 目标rect
                );
                canvas.save();
                canvas.clipRect(r);
                mUnselectedDrawable.draw(canvas);
                canvas.restore();

            }

            {
                // 2、画彩色区域
                int w = bounds.width();
                int h = bounds.height();
                int gravity = ratio < 0 ? Gravity.RIGHT : Gravity.LEFT;
                w -= (int) (w*Math.abs(ratio));
                Gravity.apply(
                        gravity, // 从哪个方向开始剪，左边还是右边
                        w,    // 目标矩形的宽
                        h,      // 目标矩形的高
                        bounds, // 被剪裁图片的rect
                        r       // 目标rect
                );
                canvas.save();
                canvas.clipRect(r);
                mSelectedDrawable.draw(canvas);
                canvas.restore();
            }

        }


    }


    @Override
    protected boolean onLevelChange(int level) {
        // 当设置level时，来重绘Drawable
        invalidateSelf();
        return super.onLevelChange(level);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        // 定好两张图片的宽高
        mUnselectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);
    }

    @Override
    public int getIntrinsicWidth() {
        return Math.max(mSelectedDrawable.getIntrinsicWidth(),
                mUnselectedDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        return Math.max(mSelectedDrawable.getIntrinsicHeight(),
                mSelectedDrawable.getIntrinsicHeight());
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}

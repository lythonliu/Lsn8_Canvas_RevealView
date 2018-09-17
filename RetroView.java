package com.example.myui.v07;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.myui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class RetroView extends View {

    public RetroView(Context context) {
        this(context,null);
    }

    public RetroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 图片资源
     */
    private static final int[] imgResDark = {R.mipmap.avft,R.mipmap.box_stack,
            R.mipmap.bubble_frame,R.mipmap.bubbles,
            R.mipmap.bullseye,R.mipmap.circle_filled,R.mipmap.circle_outline};
    private static final int[] imgResLight = {R.mipmap.avft_active,R.mipmap.box_stack_active,
            R.mipmap.bubble_frame_active ,R.mipmap.bubbles_active,
            R.mipmap.bullseye_active,R.mipmap.circle_filled_active,R.mipmap.circle_outline_active};


    /**
     * view
     */
    private Paint mPaint;
    private List<Bitmap> darkBitmaps,lightBitmaps;
    private int width = 300;//一张图的宽度
    private int startX,endX;//最左边x和最右边x
    private int currentX;//当前x
    private GestureDetector mGesture;
    private void init(Context context) {
        //初始化各种资源
        darkBitmaps = new ArrayList<>();
        lightBitmaps = new ArrayList<>();
        for(int i = 0;i < imgResDark.length; i++){
            darkBitmaps.add(BitmapFactory.decodeResource(context.getResources(),imgResDark[i]));
            lightBitmaps.add(BitmapFactory.decodeResource(context.getResources(),imgResLight[i]));
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGesture = new GestureDetector(context,linstener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startX = -(w/2 - width/2);
        endX = width*imgResDark.length - w - startX;
        currentX = startX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(-currentX,0);
        for (int i=0 ; i<darkBitmaps.size() ; i++){
            canvas.drawBitmap(darkBitmaps.get(i),null,new RectF(width*i,0,width*(i+1),width),mPaint);
        }
        canvas.clipRect(currentX+getWidth()/2-width/2,0,currentX+getWidth()/2+width/2,width);
        for (int i=0 ; i<lightBitmaps.size() ; i++){
            canvas.drawBitmap(lightBitmaps.get(i),null,new RectF(width*i,0,width*(i+1),width),mPaint);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGesture.onTouchEvent(event);
        return true;
    }

    MyListener linstener = new MyListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            currentX += distanceX;
            currentX = Math.min(endX,Math.max(startX,currentX));
            postInvalidate();
            Log.d("Retro","currentX="+currentX+",distanceX"+distanceX);
            return true;
        }
    };
    abstract class MyListener implements GestureDetector.OnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }


}

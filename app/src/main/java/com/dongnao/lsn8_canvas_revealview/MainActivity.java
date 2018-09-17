package com.dongnao.lsn8_canvas_revealview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends com.lythonliu.LinkAppCompatActivity {
    @Override
    public String getAppName(){
        return BuildConfig.APP_NAME;
    }
    private ImageView iv;

    private  int level = 10000;

    private int[] mImgIds = new int[] { //7个
            R.drawable.avft,
            R.drawable.box_stack,
            R.drawable.bubble_frame,
            R.drawable.bubbles,
            R.drawable.bullseye,
            R.drawable.circle_filled,
            R.drawable.circle_outline,

            R.drawable.avft,
            R.drawable.box_stack,
            R.drawable.bubble_frame,
            R.drawable.bubbles,
            R.drawable.bullseye,
            R.drawable.circle_filled,
            R.drawable.circle_outline
    };
    private int[] mImgIds_active = new int[] {
            R.drawable.avft_active, R.drawable.box_stack_active, R.drawable.bubble_frame_active,
            R.drawable.bubbles_active, R.drawable.bullseye_active, R.drawable.circle_filled_active,
            R.drawable.circle_outline_active,
            R.drawable.avft_active, R.drawable.box_stack_active, R.drawable.bubble_frame_active,
            R.drawable.bubbles_active, R.drawable.bullseye_active, R.drawable.circle_filled_active,
            R.drawable.circle_outline_active
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        RevealDrawable revealDrawable = new RevealDrawable(
                getResources().getDrawable(R.drawable.avft),
                getResources().getDrawable(R.drawable.avft_active));
        iv.setImageDrawable(revealDrawable);
        iv.setImageLevel(5000);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(level > 0){
                    level -= 500;
                //}
                iv.setImageLevel(level);
            }
        });
    }
}

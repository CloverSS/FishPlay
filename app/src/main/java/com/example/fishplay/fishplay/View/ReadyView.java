package com.example.fishplay.fishplay.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.fishplay.fishplay.R;
import com.example.fishplay.fishplay.constant.ConstantUtil;

/**
 * Created by apple on 2016/11/13.
 */
public class ReadyView  extends BaseView{
    private float button_x;
    private float button_y;
    private boolean isBtChange;				// 按钮图片改变的标记
    private Bitmap button;					// 按钮图片
    private Bitmap background;				// 背景图片
    public ReadyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        paint.setTextSize(40);
        thread = new Thread(this);
    }
    // 视图改变的方法
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.surfaceChanged(arg0, arg1, arg2, arg3);
    }
    // 视图创建的方法
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        super.surfaceCreated(arg0);
        initBitmap();
        if(thread.isAlive()){
            thread.start();
        }
        else{
            thread = new Thread(this);
            thread.start();
        }
    }
    // 视图销毁的方法
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        super.surfaceDestroyed(arg0);
        release();
    }
    // 响应触屏事件的方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();
            //判断第一个按钮是否被按下
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                isBtChange = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
            }
            return true;
        }
        //响应屏幕单点移动的消息
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                isBtChange = true;
            } else {
                isBtChange = false;
            }
            return true;
        }
        //响应手指离开屏幕的消息
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            return true;
        }
        return false;
    }
    // 初始化图片资源方法
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        button = BitmapFactory.decodeResource(getResources(), R.drawable.play);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        button_x = screen_width / 2 - button.getWidth() / 2;
        button_y = screen_height / 2 ;
    }
    // 释放图片资源的方法
    @Override
    public void release() {
        // TODO Auto-generated method stub
        if (!button.isRecycled()) {
            button.recycle();
        }
        if (!background.isRecycled()) {
            background.recycle();
        }
    }
    // 绘图方法
    @Override
    public void drawSelf() {
        // TODO Auto-generated method stub
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK); 						// 绘制背景色
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);					// 计算背景图片与屏幕的比例
            canvas.drawBitmap(background, 0, 0, paint); 		// 绘制背景图
            canvas.restore();
            canvas.drawBitmap(button, button_x, button_y, paint);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }
    // 线程运行的方法
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }
}

package com.example.fishplay.fishplay.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.fishplay.fishplay.R;

import java.util.Random;

/**
 * Created by apple on 2016/11/13.
 */
public class MiddleFish extends EnemyFish {

    private static int currentCount = 0;	 //	对象当前的数量
    public static int sumCount = 6;	 	 	 //	对象总的数量
    private Bitmap middleFish; // 对象图片

    public MiddleFish(Resources resources) {
        super(resources);
        // TODO Auto-generated constructor stub
        this.score = 60;		// 为对象设置分数
    }
    //初始化数据
    @Override
    public void initial(int arg0,float arg1,float arg2){
        isAlive = true;
        Random ran = new Random();
        speed = ran.nextInt(2) + 4 * arg0;
        object_y = ran.nextInt((int)(screen_height - object_height));
        int newleft=ran.nextInt(2);
        if(fromleft!=newleft)
        {
            change();
        }
        fromleft=newleft;
        if(fromleft==0) {
            object_x = screen_width + (object_width-50) * (currentCount * 2 );
        }
        else
             object_x = -(object_width-50) * (currentCount*2 );
        currentCount++;
        if(currentCount >= sumCount){
            currentCount = 0;
        }
    }
    // 初始化图片资源
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        fromleft=1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=1;
        options.inJustDecodeBounds=false;
        middleFish = BitmapFactory.decodeResource(resources, R.drawable.middlefish,options);
        object_width = middleFish.getWidth();			//获得每一帧位图的宽
        object_height = middleFish.getHeight();		//获得每一帧位图的高
    }
    // 对象的绘图函数
    @Override
    public void drawSelf(Canvas canvas) {
        // TODO Auto-generated method stub
        if(isAlive){
                if(isVisible){
                    canvas.save();
                    canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
                    canvas.drawBitmap(middleFish, object_x, object_y,paint);
                    canvas.restore();
                }
            logic();
            }
    }
    public void change()
    {
        int changeWidth=middleFish.getWidth();
        int changeHeight=middleFish.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 长和宽放大缩小的比例
        Bitmap newBmp = Bitmap.createBitmap(middleFish,0,0,changeWidth,changeHeight,matrix,true);
        middleFish=newBmp;
    }
    // 释放资源
    @Override
    public void release() {
        // TODO Auto-generated method stub
        if(!middleFish.isRecycled()){
            middleFish.recycle();
        }
    }
}



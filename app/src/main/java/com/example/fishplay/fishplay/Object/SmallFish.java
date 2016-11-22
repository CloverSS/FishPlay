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
public class SmallFish extends EnemyFish {
    private static int currentCount = 0;     //	对象当前的数量
    private Bitmap smallFish;// 对象图片
    public static int sumCount = 7;             //	对象总的数量

    public SmallFish(Resources resources) {
        super(resources);
        // TODO Auto-generated constructor stub
        this.score = 30;
    }

    //初始化数据
    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        Random ran = new Random();
        speed = ran.nextInt(2) + 6 * arg0;
        object_y = ran.nextInt((int) (screen_height- object_height));
        int newleft=ran.nextInt(2);
        if(fromleft!=newleft)
        {
            change();
        }
        fromleft=newleft;
        if(fromleft==0) {
            object_x = screen_width + (object_width-10) * (currentCount * 2 +1);
        }
        else
             object_x = -(object_width-10) * (currentCount * 2+1);
        currentCount++;
        if (currentCount >= sumCount) {
            currentCount = 0;
        }
    }

    // 初始化图片资源
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        fromleft=1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=3;
        options.inJustDecodeBounds=false;
        smallFish = BitmapFactory.decodeResource(resources, R.drawable.smallfish,options);
        object_width = smallFish.getWidth();            //获得每一帧位图的宽
        object_height = smallFish.getHeight() ;        //获得每一帧位图的高
    }

    // 对象的绘图函数
    @Override
    public void drawSelf(Canvas canvas) {
        // TODO Auto-generated method stub
        if (isAlive) {
                if (isVisible) {
                    canvas.save();
                    canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
                    canvas.drawBitmap(smallFish, object_x, object_y, paint);
                    canvas.restore();
                }
                logic();
            }
    }
    public void change()
    {
        int changeWidth=smallFish.getWidth();
        int changeHeight=smallFish.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 长和宽放大缩小的比例
        Bitmap newBmp = Bitmap.createBitmap(smallFish,0,0,changeWidth,changeHeight,matrix,true);
        smallFish=newBmp;
    }
    // 释放资源
    @Override
    public void release() {
        // TODO Auto-generated method stub
        if (!smallFish.isRecycled()) {
            smallFish.recycle();
        }
    }
}

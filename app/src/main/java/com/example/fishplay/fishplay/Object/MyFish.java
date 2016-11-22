package com.example.fishplay.fishplay.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.fishplay.fishplay.Factory.GameObjectFactory;
import com.example.fishplay.fishplay.Object.GameObject;
import com.example.fishplay.fishplay.R;
import com.example.fishplay.fishplay.View.MainView;


/**
 * Created by apple on 2016/11/13.
 */
public class MyFish extends GameObject {
    private float middle_x;			 // 飞机的中心坐标
    private float middle_y;
    private long startTime;	 	 	 // 开始的时间
    private long endTime;	 	 	 // 结束的时间
    private Bitmap myfish;
    private int right;// 鱼的图片
    private MainView mainView;
    private GameObjectFactory factory;
    public MyFish(Resources resources) {
        super(resources);
        // TODO Auto-generated constructor stub
        initBitmap();
        this.speed = 30;
        this.right=1;
        factory = new GameObjectFactory();
    }
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
    // 设置屏幕宽度和高度
    @Override
    public void setScreenWH(float screen_width, float screen_height) {
        super.setScreenWH(screen_width, screen_height);
        object_x = screen_width/2 - object_width/2;
        object_y = screen_height - object_height;
        middle_x = object_x + object_width/2;
        middle_y = object_y + object_height/2;
    }
    // 初始化图片资源的
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        myfish = BitmapFactory.decodeResource(resources, R.drawable.myfish);
        object_width = myfish.getWidth(); // 获得每一帧位图的宽
        object_height = myfish.getHeight(); 	// 获得每一帧位图的高
    }
    // 对象的绘图方法
    @Override
    public void drawSelf(Canvas canvas) {
        // TODO Auto-generated method stub
        if(isAlive){
            object_width = myfish.getWidth(); // 获得每一帧位图的宽
            object_height = myfish.getHeight(); 	// 获得每一帧位图的高
            int x = (int) (currentFrame * object_width); // 获得当前帧相对于位图的X坐标
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            canvas.drawBitmap(myfish, object_x - x, object_y, paint);
            canvas.restore();
        }
    }

    public void big(int score)
    {
        int BigWidth=myfish.getWidth();
        int BigHeight=myfish.getHeight();
        float sx=1+(float)score/1000;//要强制转换，不转换我的在这总是死掉。
        float sy=1+(float)score/1000;
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy); // 长和宽放大缩小的比例
        Bitmap newBmp = Bitmap.createBitmap(myfish,0,0,BigWidth,BigHeight,matrix,true);
        myfish=newBmp;
    }

    public void change()
    {
        int changeWidth=myfish.getWidth();
        int changeHeight=myfish.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 长和宽放大缩小的比例
        Bitmap newBmp = Bitmap.createBitmap(myfish,0,0,changeWidth,changeHeight,matrix,true);
        myfish=newBmp;
    }
    // 释放资源的方法
    @Override
    public void release() {
        // TODO Auto-generated method stub
        if(!myfish.isRecycled()){
            myfish.recycle();
        }
    }
    //getter和setter方法
    public void setRight(int right){this.right=right;}
    public float getRight(){return right;}
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public float getMiddle_x() {
        return middle_x;
    }
    public void setMiddle_x(float middle_x) {
        this.middle_x = middle_x;
        this.object_x = middle_x - object_width/2;
    }
    public float getMiddle_y() {
        return middle_y;
    }
    public void setMiddle_y(float middle_y) {
        this.middle_y = middle_y;
        this.object_y = middle_y - object_height/2;
    }
}

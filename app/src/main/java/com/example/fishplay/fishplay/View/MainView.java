package com.example.fishplay.fishplay.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.fishplay.fishplay.Factory.GameObjectFactory;
import com.example.fishplay.fishplay.Object.BigFish;
import com.example.fishplay.fishplay.Object.EnemyFish;
import com.example.fishplay.fishplay.Object.GameObject;
import com.example.fishplay.fishplay.Object.LittleFish;
import com.example.fishplay.fishplay.Object.MiddleFish;
import com.example.fishplay.fishplay.Object.MyFish;
import com.example.fishplay.fishplay.Object.SmallFish;
import com.example.fishplay.fishplay.R;
import com.example.fishplay.fishplay.constant.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2016/11/13.
 */
public class MainView extends BaseView{
    private int sumScore;			// 游戏总得分
    private int speedTime;			// 游戏速度的倍数
    private float bg_y;				// 图片的坐标
    private float play_bt_w;
    private float play_bt_h;
    private float missile_bt_y;
    private boolean isPlay;			// 标记游戏运行状态
    private boolean isTouchPlane;	// 判断玩家是否按下屏幕
    private Bitmap background; 		// 背景图片
    private MyFish myFish;		// 玩家的鱼
    private List<EnemyFish> enemyFishs;
    private GameObjectFactory factory;
    public MainView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        isPlay = true;
        speedTime = 5;
        factory = new GameObjectFactory();						  //水族馆类
        enemyFishs = new ArrayList<EnemyFish>();
        myFish = (MyFish) factory.createMyFish(getResources());//生产玩家的鱼
        myFish.setMainView(this);
        for(int i = 0;i < LittleFish.sumCount;i++){
            //生产小型敌机
            LittleFish littleFish = (LittleFish) factory.createLittleFish(getResources());
            enemyFishs.add(littleFish);
        }
        for(int i = 0; i < SmallFish.sumCount; i++){
            //生产中型敌机
            SmallFish smallFish = (SmallFish) factory.createSmallFish(getResources());
            enemyFishs.add(smallFish);
        }
        for(int i = 0; i < MiddleFish.sumCount; i++){
            //生产大型敌机
            MiddleFish middleFish = (MiddleFish) factory.createMiddleFish(getResources());
            enemyFishs.add(middleFish);
        }
        for(int i = 0; i < BigFish.sumCount; i++){
            //生产大型敌机
            BigFish bigFish = (BigFish) factory.createBigFish(getResources());
            enemyFishs.add(bigFish);
        }

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
        initBitmap(); // 初始化图片资源
        for(GameObject obj:enemyFishs){
            obj.setScreenWH(screen_width,screen_height);
        }
        myFish.setScreenWH(screen_width,screen_height);
        myFish.setAlive(true);
        thread = new Thread(this);
        thread.start();

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
        if(event.getAction() == MotionEvent.ACTION_UP){
            isTouchPlane = false;
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN) {
           float x = event.getX();
            float y = event.getY();
            if(x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h){
                if(isPlay){
                    isPlay = false;
                }
                else{
                    isPlay = true;
                    synchronized(thread){
                        thread.notify();
                    }
                }
                return true;
            }
            //判断玩家飞机是否被按下
            else if(x > myFish.getObject_x() && x < myFish.getObject_x() + myFish.getObject_width()
                    && y > myFish.getObject_y() && y < myFish.getObject_y() + myFish.getObject_height()){
                if(isPlay){
                    isTouchPlane = true;
                }
                return true;
            }

        }
        //响应手指在屏幕移动的事件
        else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
            //判断触摸点是否为玩家的飞机
            if(isTouchPlane){
                float x = event.getX();
                float y = event.getY();
                if (x > myFish.getMiddle_x() + 20) {
                    if (myFish.getMiddle_x() + myFish.getSpeed() <= screen_width) {
                        myFish.setMiddle_x(myFish.getMiddle_x() + myFish.getSpeed());
                        if (myFish.getRight() == 0) {
                            myFish.change();
                            myFish.setRight(1);
                        }
                    }
                } else if (x < myFish.getMiddle_x() - 20) {
                    if (myFish.getMiddle_x() - myFish.getSpeed() >= 0) {
                        myFish.setMiddle_x(myFish.getMiddle_x() - myFish.getSpeed());
                        if (myFish.getRight() == 1) {
                            myFish.change();
                            myFish.setRight(0);
                        }
                    }
                }
                if (y > myFish.getMiddle_y() + 20) {
                    if (myFish.getMiddle_y() + myFish.getSpeed() <= screen_height) {
                        myFish.setMiddle_y(myFish.getMiddle_y() + myFish.getSpeed());
                    }
                } else if (y < myFish.getMiddle_y() - 20) {
                    if (myFish.getMiddle_y() - myFish.getSpeed() >= 0) {
                        myFish.setMiddle_y(myFish.getMiddle_y() - myFish.getSpeed());
                    }
                }
                return true;
            }
        }
        return false;
    }
    // 初始化图片资源方法
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        bg_y = 0;
    }
    //初始化游戏对象
    public void initObject(){
        for(EnemyFish obj:enemyFishs){
            //初始化小型敌机
            if(obj instanceof LittleFish){
                if(!obj.isAlive()){
                    obj.initial(speedTime,0,0);
                    break;
                }
            }
            //初始化中型敌机
            else if(obj instanceof SmallFish){
                    if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
            }
            //初始化大型敌机
            else if(obj instanceof MiddleFish){
                    if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
            }
            else if(obj instanceof BigFish) {
                    if (!obj.isAlive()) {
                        obj.initial(speedTime, 0, 0);
                        break;
                    }
            }
        }
        if(sumScore >= speedTime*100000 && speedTime < 6){
            speedTime++;
        }
    }
    // 释放图片资源的方法
    @Override
    public void release() {
        // TODO Auto-generated method stub
        for(GameObject obj:enemyFishs){
            obj.release();
        }
        myFish.release();
        if(!background.isRecycled()){
            background.recycle();
        }
    }
    // 绘图方法
    @Override
    public void drawSelf() {
        // TODO Auto-generated method stub
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK); // 绘制背景色
            canvas.save();
            // 计算背景图片与屏幕的比例
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, bg_y, paint);   // 绘制背景图
            canvas.restore();
            //绘制敌机
            for(EnemyFish obj:enemyFishs){
                if(obj.isAlive()){
                    obj.drawSelf(canvas);
                    //检测敌机是否与玩家的飞机碰撞
                    if(obj.isCanCollide() && myFish.isAlive()){
                        if(obj.isCollide(myFish)==1){
                            myFish.setAlive(false);
                        }
                        else if(obj.isCollide(myFish)==2)
                        {
                            obj.setAlive(false);
                            myFish.big(obj.getScore());
                            addGameScore(obj.getScore());
                        }
                    }
                }
            }
            if(!myFish.isAlive()){
                threadFlag = false;
            }
            myFish.drawSelf(canvas);	//绘制玩家的飞机
            //绘制积分文字
            paint.setTextSize(50);
            paint.setColor(Color.rgb(235, 161, 1));
            canvas.drawText("积分:"+String.valueOf(sumScore), 30, 40, paint);		//绘制文字
            } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }
    // 增加游戏分数的方法
    public void addGameScore(int score){
        sumScore += score;			// 游戏总得分
    }

    // 线程运行的方法
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            initObject();
            drawSelf();
            long endTime = System.currentTimeMillis();
            if(!isPlay){
                synchronized (thread) {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                if (endTime - startTime < 100)
                    Thread.sleep(100 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       Message message = new Message();
        message.what = 	ConstantUtil.TO_END_VIEW;
        message.arg1 = Integer.valueOf(sumScore);
        mainActivity.getHandler().sendMessage(message);
    }
}


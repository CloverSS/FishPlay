package com.example.fishplay.fishplay.Factory;

import android.content.res.Resources;

import com.example.fishplay.fishplay.Object.BigFish;
import com.example.fishplay.fishplay.Object.GameObject;
import com.example.fishplay.fishplay.Object.LittleFish;
import com.example.fishplay.fishplay.Object.MiddleFish;
import com.example.fishplay.fishplay.Object.MyFish;
import com.example.fishplay.fishplay.Object.SmallFish;

/**
 * Created by apple on 2016/11/13.
 */
public class GameObjectFactory {
    //创建小型敌机的方法
    public GameObject createLittleFish(Resources resources){
        return new LittleFish(resources);
    }
    //创建中型敌机的方法
    public GameObject createSmallFish(Resources resources){
        return new SmallFish(resources);
    }
    //创建大型敌机的方法
    public GameObject createMiddleFish(Resources resources){
        return new MiddleFish(resources);
    }
    //创建BOSS敌机的方法
    public GameObject createBigFish(Resources resources){
        return new BigFish(resources);
    }
    //创建玩家飞机的方法
    public GameObject createMyFish(Resources resources){
        return new MyFish(resources);
    }

}

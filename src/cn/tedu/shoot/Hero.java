package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//英雄機
//繼承飛行物
public class Hero extends FlyingObject{
    private int life;//命
    private int fire;//火力值

    //構造方法
    public Hero(){
        super(91,139,140,400);
        life = 3;//英雄機初始生命
        fire = 0;//英雄機初始火力
    }

    //重寫getImage
    private int index = 0;
    //每10毫秒跑一次圖
    //heros[0],heros[1]來回切換
    public BufferedImage getImage(){
        return Images.heros[index++%Images.heros.length];//返回英雄機圖片
    }
}

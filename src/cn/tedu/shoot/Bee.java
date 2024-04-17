package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

//小蜜蜂
//繼承飛行物
public class Bee extends FlyingObject {
    private int xSpeed;//x座標移動速度
    private int ySpeed;//y座標移動速度
    private int awardType; //英雄機打到之後給予獎勵類型

    //構造方法
    public Bee() {
        super(60, 51);
        Random rand = new Random();//隨機數
        xSpeed = 1;//小蜜蜂圖片x座標移動速度,左右移動
        ySpeed = 2;//小蜜蜂圖片y座標移動速度,朝下移動
        awardType = rand.nextInt(2);//0到1之間的隨機數
    }

    //重寫getImage
    private int index = 1;

    public BufferedImage getImage() {
        if (isLive()) {//若活者
            return Images.bees[0]; //則直接返回bees[0]圖片
        } else if (isDead()) {//若死的
            BufferedImage img = Images.bees[index++];//獲取爆破圖
            if (index == Images.bees.length) {//5為最後一張,到最後一張,則將當前狀態修改為REMOVE刪除
                state = REMOVE;
            }
            return img;//返回爆破圖
        }
        return null;//死的和刪除的,不返回圖片
    }

    //重寫step() 飛行物移動
    public void step() {
        x += xSpeed;//向左或右
        y += ySpeed;//向下
        if (x <= 0 || x >= World.WIDTH - width) {
           xSpeed *= -1;//則切換方向(正變負,負變正)
        }
    }
}

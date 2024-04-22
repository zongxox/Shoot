package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

//大敵機
//繼承飛行物
public class BigAirplane extends FlyingObject implements EnemyScore{
    private int speed;//移動速度

    //構造方法
    public BigAirplane() {
        super(66, 89);
        speed = 2;//大敵機圖片移動速度
    }

    //重寫getImage
    private int index = 1;

    public BufferedImage getImage() {
        if (isLive()) {//若活者
            return Images.bairs[0]; //則直接返回bairs[0]圖片
        } else if (isDead()) {//若死的
            BufferedImage img = Images.bairs[index++];//獲取爆破圖
            if (index == Images.bairs.length) {//5為最後一張,到最後一張,則將當前狀態修改為REMOVE刪除
                state = REMOVE;
            }
            return img;//返回爆破圖
        }
        return null;//死的和刪除的,不返回圖片
    }

    //重寫step() 飛行物移動
    public void step() {
        y += speed;//向下
    }
    //重寫getScore得分
    @Override
    public int getScore() {
        //打掉小敵機,玩家得3分
        return 3;
    }
}

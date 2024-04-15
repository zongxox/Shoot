package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

//小敵機
//繼承飛行物
public class Airplane extends FlyingObject {
    private int speed;//移動速度

    //構造方法
    public Airplane() {
        super(48, 50);
        speed = 2;//小敵機圖片移動速度
    }

    //重寫getImage
    private int index = 1;

    public BufferedImage getImage() {
        if (isLive()) {//若活者
            return Images.airs[0]; //則直接返回airs[0]圖片
        } else if (isDead()) {//若死的
            BufferedImage img = Images.airs[index++];//獲取爆破圖
            if (index == Images.airs.length) {//5為最後一張,到最後一張,則將當前狀態修改為REMOVE刪除
                state = REMOVE;
            }
            return img;//返回爆破圖
        }
        return null;//死的和刪除的,不返回圖片
    }

}

package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//子彈
//繼承飛行物
public class Bullet extends FlyingObject {
    private int speed;//移動速度

    //構造方法
    public Bullet(int x, int y) {
        super(8, 20, x, y);
        speed = 3;
    }

    //重寫getImage
    public BufferedImage getImage() {
        if (isLive()) {//若活者
            return Images.bullet; //則直接返回子彈圖片
        } else if (isDead()) {//若死的
            state = REMOVE;//則將當前狀態修改為REMOVE刪除
        }
        return null;//死的和刪除的,不返回圖片
    }

    //重寫step() 飛行物移動
    public void step() {
        y -= speed;//向上
    }

    //重寫isOutOfBounds檢測子彈是否超過視窗
    public boolean isOutOfBounds(){
        return y <= -height; //子彈超過窗口的高,代表超出窗口
    }
}


package cn.tedu.shoot;

import java.util.Random;
import java.awt.image.BufferedImage;

//飛行物
public abstract class FlyingObject {
    //狀態
    public static final int LIVE = 0;//活者的
    public static final int DEAD = 1;//爆破中
    public static final int REMOVE = 2;//刪除的
    protected int state = LIVE; //當前狀態(默認為活者)


    protected int width;//寬
    protected int height;//高
    protected int x;//x座標
    protected int y;//y座標

    //構造方法:專門給小敵機,大敵機,小蜜蜂提供的因為他們的x跟y都是一樣的
    //三種敵人的寬和高都是不同的,所以數據不能寫死,需傳參寫活
    //三種敵人的x和y都是相同的,所以數據直接寫死,不需要傳參數
    FlyingObject(int width, int height) {
        this.width = width;//圖片寬
        this.height = height;//圖片高
        Random rand = new Random();//隨機數
        x = rand.nextInt(World.WIDTH - width);//圖片x座標,x:0到(窗口寬-圖片寬度)內的隨機數,初始座標
        y = -height;//敵人圖片y座標,初始座標
    }

    //構造方法:專門給英雄機,天空,子彈提供的
    public FlyingObject(int width, int height, int x, int y) {
        this.width = width;//圖片寬
        this.height = height;//圖片高
        this.x = x;//圖片x座標
        this.y = y;//圖片y座標
    }

    //獲取對象的圖片
    public abstract BufferedImage getImage();

    //判斷對象狀態是否活者
    public boolean isLive() {
        return state == LIVE;
    }

    //判斷對象狀態是否爆破
    public boolean isDead() {
        return state == DEAD;
    }

    //判斷對象狀態是否死的
    public boolean isRemove() {
        return state == REMOVE;
    }

    //飛行物移動
    public abstract void step();

    //檢測敵人是否超過視窗
    public boolean isOutOfBounds() {
        return y >= World.HEIGHT; //敵人超過窗口的高,代表超出窗口
    }

    //檢測碰撞
    public boolean isHit(FlyingObject other) {
        int x1 = this.x - other.width; //x1:敵人的x - 子彈or英雄機的寬
        int x2 = this.x + this.width;  //x2:敵人的x + 敵人寬
        int y1 = this.y - other.height;//y1:敵人的y - 子彈or英雄機的高
        int y2 = this.y + this.height; //y2:敵人的y + 敵人高
        int x = other.x;               //子彈or英雄機的x
        int y = other.y;               //子彈or英雄機的y

        //x在x1與x2之間,並且,y在y1與y2之間,即為撞上了
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    //飛行物死去
    public void goDead(){
        //將對象狀態修改為DEAD
        state = DEAD;
    }


}

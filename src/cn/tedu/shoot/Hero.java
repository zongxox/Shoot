package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//英雄機
//繼承飛行物
public class Hero extends FlyingObject {
    private int life;//命
    private int fire;//火力值

    //構造方法
    public Hero() {
        super(91, 139, 140, 400);
        life = 3;//英雄機初始生命
        fire = 0;//英雄機初始火力
    }

    //重寫getImage
    private int index = 0;

    //每10毫秒跑一次圖
    //heros[0],heros[1]來回切換
    public BufferedImage getImage() {
        return Images.heros[index++ % Images.heros.length];//返回英雄機圖片
    }

    //英雄機生成子彈
    public Bullet[] shoot() {
        int xStep = this.width / 4;//1/4英雄機的寬
        int yStep = 20;//子彈距離

        if (fire > 0) {//2發子彈
            Bullet[] bs = new Bullet[2];//2發子彈
            //x:英雄機的x+1/4英雄機的寬
            bs[0] = new Bullet(this.x + 1 * xStep, this.y - yStep);
            //x:英雄機的x+3/4英雄機的寬
            bs[1] = new Bullet(this.x + 3 * xStep, this.y - yStep);
            fire -= 2;//發射一次2發子彈,則火力值減2
            return bs;
        } else {//1發子彈
            Bullet[] bs = new Bullet[1];//2發子彈
            //x:英雄機的x+2/4英雄機的寬
            //y:英雄機的y-固定的20
            bs[0] = new Bullet(this.x + 2 * xStep, this.y - yStep);
            return bs;
        }
    }

    //重寫step() 飛行物移動
    public void step() {
    }

    //英雄機移動,跟滑鼠一起移動,滑鼠x座標,滑鼠y座標
    public void moveTo(int x, int y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }

    //英雄機增加火力
    public void addFire(){
        fire += 40;//火力增加40
    }

    //英雄機增加命
    public void addLife(){
        life++;//英雄機命數增加1
    }

    //獲取英雄機命數
    public int getLife(){
        return life;//返回命數
    }

    //英雄機減少1命
    public void subtractLife(){
        life --;//命數減1
    }

    //初始化英雄機火力值
    public void clearFire(){
        fire = 0;//火力值初始化
    }
}

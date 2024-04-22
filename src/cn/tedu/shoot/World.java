package cn.tedu.shoot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//遊戲視窗
public class World extends JPanel {
    public static final int WIDTH = 400;//視窗寬
    public static final int HEIGHT = 700;//視窗高
    public static final int START = 0;   //啟動狀態
    public static final int RUNNING = 1; //運行狀態
    public static final int PAUSE = 2;   //暫停狀態
    public static final int GAME_OVER = 3;   //結束狀態
    private int state = START; //當前狀態(默認為啟動狀態)


    //窗口天空對象
    private Sky sky = new Sky();

    //窗口英雄機對象
    private Hero hero = new Hero();

    //窗口敵人(小敵機,大敵機,小蜜蜂)數組
    private FlyingObject[] enemies = {};

    //子彈數組
    private Bullet[] bullets = {};

    //生成敵人
    public FlyingObject nextOne() {
        Random rand = new Random();//隨機數
        int type = rand.nextInt(20);//0到19
        if (type < 5) {//0到4返回小蜜蜂對象
            return new Bee();
        } else if (type < 13) {//5到12返回小敵機對象
            return new Airplane();
        } else {//13到19返回大敵機
            return new BigAirplane();
        }
    }


    //敵人入場記數
    private int enterIndex = 0;

    //敵人入場
    public void enterAction() {//每10毫秒走一次
        enterIndex++;//每10毫秒增1
        if (enterIndex % 40 == 0) {//每400(40*10)毫秒走一次
            FlyingObject obj = nextOne();//獲取敵人對象
            enemies = Arrays.copyOf(enemies, enemies.length + 1);//數組擴充
            enemies[enemies.length - 1] = obj;//將obj添加到enemies的最後一個元素上

        }
    }

    private int shootIndex = 0;

    //子彈入場
    public void shootAction() {//每10毫秒走一次
        shootIndex++;//每10毫秒增1
        if (shootIndex % 30 == 0) {//每300(30*10)毫秒走一次
            Bullet[] bs = hero.shoot();//獲取英雄機發射出來的子彈數組
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);//數組擴充
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);//數組的追加
        }
    }

    //飛行物移動
    public void stepAction() {//每10毫秒走一次
        sky.step();//飛行物移動
        for (int i = 0; i < enemies.length; i++) {//循環所有敵人
            enemies[i].step();//敵人移動
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();//子彈移動
        }
    }

    //刪除超過視窗的子彈和敵人,優化內存
    public void outOfBoundsAction(){
        for (int i = 0; i < enemies.length; i++){//敵人
            if (enemies[i].isOutOfBounds() || enemies[i].isRemove()){
                enemies[i] = enemies[enemies.length-1];
                enemies = Arrays.copyOf(enemies, enemies.length-1);
            }
        }
        for (int i = 0; i < bullets.length; i++){//子彈
            if (bullets[i].isOutOfBounds() || bullets[i].isRemove()){
                bullets[i] = bullets[bullets.length-1];
                bullets = Arrays.copyOf(bullets, bullets.length-1);
            }
        }
    }

    //玩家的得分
    private int score = 0;

    //子彈與敵人的碰撞
    public void  bulletBangAction(){//每10毫秒跑一次
        for (int i = 0; i < bullets.length; i++) {//循環子彈
            Bullet b = bullets[i];//獲取每一個子彈
            for (int j = 0; j < enemies.length; j++) {//循環敵人
                FlyingObject f = enemies[j];//獲取每一個敵人
                if (b.isLive() && f.isLive() && f.isHit(b)){//若都活者並且還撞上了
                    b.goDead();//子彈死去
                    f.goDead();//敵人死去
                    if (f instanceof EnemyScore){//若被撞對象能得分
                        EnemyScore es = (EnemyScore) f;//將被撞對象強轉為得分接口
                        score += es.getScore();//玩家得分
                    }
                    if (f instanceof EnemyAward){//若被撞對象能得獎例
                        EnemyAward ea = (EnemyAward) f;//將被撞對象強轉為獎勵接口
                        int type = ea.getAwardType();//獲取獎勵類型
                        switch (type){//根據獎勵類型來獲取不同的獎勵
                            case EnemyAward.FIRE://若為火力值
                                hero.addFire();  //則英雄機增加火力
                                break;
                            case EnemyAward.LIFE://若為命
                                hero.addLife();  //則英雄機增加命
                                break;
                        }
                    }
                }
            }
        }
    }

    //英雄機與敵人的碰撞
    public void heroBangAction(){
        for (int i = 0; i < enemies.length; i++) {//循環所有敵人
            FlyingObject f = enemies[i];//獲取每一個敵人
            if (hero.isLive() && f.isLive() && f.isHit(hero)){//若都活者,並且撞上了
                f.goDead();//敵人去死
                hero.subtractLife();//英雄機減命
                hero.clearFire();//英雄機火力初始化
            }
        }
    }

    //檢測遊戲結束
    public void checkGameOverAction(){
        if (hero.getLife() <= 0){//若英雄機命數<=0,表示遊戲結束
            state = GAME_OVER; //則當前狀態修改為遊戲結束狀態
        }
    }

    //啟動代碼的執行
    public void action() {
        //滑鼠監聽器
        MouseAdapter m = new MouseAdapter() {
            //重寫mouseMoved滑鼠移動事件
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING){//僅在運行狀態下執行
                    int x = e.getX();//獲取滑鼠x座標
                    int y = e.getY();//獲取滑鼠x座標
                    hero.moveTo(x, y);//英雄機移動
                }
            }

            //重寫mouseClicked滑鼠點擊事件
            public void mouseClicked(MouseEvent e){
                switch (state){//根據當前不同狀態做不同處裡
                    case START:          //啟動狀態時
                        state = RUNNING; //修改為運行狀態
                        break;
                    case GAME_OVER:      //遊戲結束狀態時
                        score = 0;       //遊戲初始化
                        sky = new Sky();
                        hero = new Hero();
                        enemies = new FlyingObject[0];
                        bullets = new Bullet[0];
                        state = START;   //修改為啟動狀態
                        break;
                }
            }

            //重寫mouseExited滑鼠移出視窗事件
            public void mouseExited(MouseEvent e){
                if (state == RUNNING){//運行狀態時
                    state = PAUSE;    //修改為暫停狀態
                }
            }

            //重寫mouseEntered滑鼠移入視窗事件
            public void mouseEntered(MouseEvent e){
                if (state == PAUSE){//暫停狀態時
                    state = RUNNING;//修改為運行狀態
                }
            }

            };//滑鼠監聽器
        this.addMouseListener(m);
        this.addMouseMotionListener(m);

        Timer timer = new Timer();//定時器對象
        int intervel = 10;//定時間格
        timer.schedule(new TimerTask() {
            @Override
            public void run() {//每10毫秒跑一次
                if (state == RUNNING){//僅在運行狀態下執行
                    enterAction();//每10毫秒敵人入場一次
                    shootAction();//每10毫秒子彈入場一次
                    stepAction();//飛行物移動
                    outOfBoundsAction();//刪除超過視窗的子彈和敵人
                    bulletBangAction();//子彈與敵人的碰撞
                    heroBangAction();//英雄機與敵人的碰撞
                    checkGameOverAction();//檢測遊戲結束
                }
                repaint();//重新調用paint
            }
        }, intervel, intervel);//定時計畫表

    }

    //重寫paint
    public void paint(Graphics g) {
        g.drawImage(sky.getImage(), sky.x, sky.y, null);//背景1
        g.drawImage(sky.getImage(), sky.x, sky.gety1(), null);//背景2
        g.drawImage(hero.getImage(), hero.x, hero.y, null);//英雄機
        for (int i = 0; i < enemies.length; i++) {//所有敵人
            FlyingObject f = enemies[i]; //獲取每一個敵人
            g.drawImage(f.getImage(), f.x, f.y, null);//畫敵人
        }
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.x, b.y, null);//畫子彈
        }
        g.drawString("SCORE:" + score,10,25 );//視窗左上分數
        g.drawString("LIFE:" + hero.getLife(), 10, 45);//視窗左上命數

        switch (state){//根據當前狀態畫不同的圖
            case START: //啟動狀態下畫啟動圖
                g.drawImage(Images.start, 0, 0, null);
                break;
            case PAUSE: //暫停狀態下畫暫停圖
                g.drawImage(Images.pause, 0, 0, null);
                break;
            case GAME_OVER: //遊戲結束狀態下畫遊戲結束圖
                g.drawImage(Images.gameover, 0, 0, null);
                break;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        world.action();//啟動代碼執行
    }
}

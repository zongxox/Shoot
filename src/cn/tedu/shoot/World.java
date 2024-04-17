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
            if (enemies[i].isOutOfBounds()){
                enemies[i] = enemies[enemies.length-1];
                enemies = Arrays.copyOf(enemies, enemies.length-1);
            }
        }
        for (int i = 0; i < bullets.length; i++){//子彈
            if (bullets[i].isOutOfBounds()){
                bullets[i] = bullets[bullets.length-1];
                bullets = Arrays.copyOf(bullets, bullets.length-1);
            }
        }
    }

    //啟動代碼的執行
    public void action() {
        //滑鼠監聽器
        MouseAdapter m = new MouseAdapter() {
            //重寫mouseMoved滑鼠移動事件
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();//獲取滑鼠x座標
                int y = e.getY();//獲取滑鼠x座標
                hero.moveTo(x, y);//英雄機移動
            }
        };//滑鼠監聽器
        this.addMouseListener(m);
        this.addMouseMotionListener(m);

        Timer timer = new Timer();//定時器對象
        int intervel = 10;//定時間格
        timer.schedule(new TimerTask() {
            @Override
            public void run() {//每10毫秒跑一次
                enterAction();//每10毫秒敵人入場一次
                shootAction();//每10毫秒子彈入場一次
                stepAction();//飛行物移動
                outOfBoundsAction();//刪除超過視窗的子彈和敵人
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
            g.drawImage(f.getImage(), f.x, f.y, null);//循環敵人
        }
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.x, b.y, null);//循環子彈
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

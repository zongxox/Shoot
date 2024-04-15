package cn.tedu.shoot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

//圖片工具類
public class Images {
    public static BufferedImage sky;    //天空圖片
    public static BufferedImage bullet; //子彈
    public static BufferedImage[] heros;//英雄機圖片數組
    public static BufferedImage[] airs; //小敵機圖片數組
    public static BufferedImage[] bairs;//大敵機圖片數組
    public static BufferedImage[] bees; //小蜜蜂圖片數組

    //給圖片做初始化
    static {
        sky = readImage("background.png");//天空
        bullet = readImage("bullet.png");//子彈
        heros = new BufferedImage[2]; //兩張圖片
        heros[0] = readImage("hero0.png");//英雄機圖片
        heros[1] = readImage("hero1.png");

        //敵人圖片
        airs = new BufferedImage[5]; //6張圖片
        bairs = new BufferedImage[5];//6張圖片
        bees = new BufferedImage[5]; //6張圖片
        airs[0] = readImage("airplane0.png");
        //airs[1] = readImage("airplane1.png");
        bairs[0] = readImage("bigairplane0.png");
        //bairs[1] = readImage("bigairplane1.png");
        bees[0] = readImage("bee0.png");
        //bees[1] = readImage("bee1.png");

        //循環爆破圖
        for (int i = 1; i < airs.length; i++) {
            airs[i] = readImage("bom" + i + ".png");
            bairs[i] = readImage("bom" + i + ".png");
            bees[i] = readImage("bom" + i + ".png");
        }
    }

    //讀取圖片 fileName:圖片名
    public static BufferedImage readImage(String fileName) {
        try {
            BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}


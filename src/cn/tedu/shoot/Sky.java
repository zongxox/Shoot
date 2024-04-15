package cn.tedu.shoot;
import java.awt.image.BufferedImage;
//天空
//繼承飛行物
public class Sky extends FlyingObject{
    private int speed;//移動速度
    private int y1;//第二個天空圖片的y座標

    //構造方法
    public Sky(){
        super(World.WIDTH,World.HEIGHT,0,0);
        speed = 1;//移動速度
        y1 = -700;//第二個天空圖片的y座標
    }

    //重寫getImage
    public BufferedImage getImage(){
        return Images.sky;//返回sky圖片
    }

    //獲取y1座標
    public int gety1(){
        return y1;
    }
}

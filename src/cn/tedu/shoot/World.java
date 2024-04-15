package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

//遊戲視窗
public class World extends JPanel{
    public static final int WIDTH = 400;//視窗寬
    public static final int HEIGHT = 700;//視窗高

    //天空對象
    private Sky sky = new Sky();

    //英雄機對象
    private Hero hero = new Hero();

    //敵人(小敵機,大敵機,小蜜蜂)數組
    private FlyingObject[] enemies = {
            new Airplane(),
            new BigAirplane(),
            new Bee()
    };

    //子彈數組
    private Bullet[] bullets = {
            new Bullet(100, 200)
    };

    //重寫paint
    public void paint(Graphics g){

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

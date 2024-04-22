package cn.tedu.shoot;
//獎勵接口
public interface EnemyAward {
    public int FIRE = 0;//火力值
    public int LIFE = 1;//命

    //獲取獎勵類型(0或1)
    public int getAwardType();
}

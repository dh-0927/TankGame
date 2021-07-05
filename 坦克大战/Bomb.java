package tankgame;

//炸弹类，出现爆炸效果
public class Bomb {
    int x, y; //坐标
    int life = 24; //生命周期
    boolean isLive = true; //是否存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {
        if (life > 0)
            life--;
        else
            isLive = false;
    }
}

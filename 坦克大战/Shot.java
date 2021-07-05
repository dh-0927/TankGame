package tankgame;

import java.io.Serializable;

public class Shot implements Runnable, Serializable{
    boolean isPause = false;
    //判断是否暂停，如果暂停则子弹退出线程，不移动
    int x; //子弹的x坐标
    int y; //子弹的y坐标
    Direction d; //子弹的方向
    int speed; //子弹的速度
    boolean isLive = true; //子弹是否存活
    int bulletSize; //子弹的大小

    public Shot(int x, int y, Direction d, int speed) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.speed = speed;
    }

    @Override
    public void run() {
        while (!isPause) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据射出的方向进行移动
            switch (d) {
                case UP:
                    y -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
            }
            //当子弹碰到敌人坦克或则边界时退出
            if (x >= 1000 || y >= 750 || x <= 0 || y <= 0 || isLive == false) {
                isLive = false;
                break;
            }
        }
    }
}

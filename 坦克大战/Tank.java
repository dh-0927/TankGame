package tankgame;

import java.io.Serializable;

public class Tank implements Serializable {
    private int x; //坦克的x坐标
    private int y; //坦克的y坐标
    private Direction d; //坦克的方向
    private int speed; //坦克的速度
    boolean isLive = true; //坦克是否存活
    boolean isPause = false;
    //判断游戏是否暂停，暂停的话退出线程

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getD() {
        return d;
    }

    public void setD(Direction d) {
        this.d = d;
    }

    public void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //上右下左移动一个像素方法
    public void moveUp() {
        for (int i = 0; i < speed; i++) {
            y -= 1;
            sleep(8);
        }
    }
    public void moveRight() {
        for (int i = 0; i < speed; i++) {
            x += 1;
            sleep(8);
        }
    }
    public void moveDown() {
        for (int i = 0; i < speed; i++) {
            y += 1;
            sleep(8);
        }
    }
    public void moveLeft() {
        for (int i = 0; i < speed; i++) {
            x -= 1;
            sleep(8);
        }
    }

}


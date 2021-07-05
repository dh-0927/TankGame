package tankgame;

import java.io.Serializable;
import java.util.Vector;

public class Hero extends Tank implements Runnable, Serializable{
    //坦克移动方向的判断，在Run()方法中实现
    Boolean U = false, R = false, D = false, L = false;
    Vector<Shot> shots = new Vector<>(); //我方坦克射出的子弹集合

    public Hero(int x, int y, Direction d) {
        super(x, y);
        setD(d);
        setSpeed(5);

    }

    public void shotEnemyTank() {
        Shot shot = null;
        int bulletSize = 6;
        int offSet = bulletSize / 2;
        //得到坦克方向，以确定炮弹方向和位置，并且创建子弹对象
        switch (getD()) {
            case UP:
                shot = (new Shot(getX() + 20 - offSet, getY(), Direction.UP, 4));
                shot.bulletSize = bulletSize;
                break;
            case RIGHT:
                shot = (new Shot(getX() + 60, getY() + 20 - offSet, Direction.RIGHT, 4));
                shot.bulletSize = bulletSize;
                break;
            case DOWN:
                shot = (new Shot(getX() + 20 - offSet, getY() + 60, Direction.DOWN, 4));
                shot.bulletSize = bulletSize;
                break;
            case LEFT:
                shot = (new Shot(getX(), getY() + 20 - offSet, Direction.LEFT, 4));
                shot.bulletSize = bulletSize;
        }
        //将子弹放入到集合中
        shots.add(shot);
        //启动子弹线程，让其动起来
        new Thread(shot).start();
    }

    @Override
    public void run() {
        //没有暂停，并且没有出游戏区域才可以移动
        while (!isPause) {
            if (getY() >= getSpeed() && U && !R && !D && !L) {
                setD(Direction.UP);
                moveUp();
            } else if (getX() + 60 <= 1000 - getSpeed() && !U && R && !D && !L) {
                setD(Direction.RIGHT);
                moveRight();
            } else if (getY() + 60 <= 750 - getSpeed() && !U && !R && D && !L) {
                setD(Direction.DOWN);
                moveDown();
            } else if (getX() >= getSpeed() && !U && !R && !D && L) {
                setD(Direction.LEFT);
                moveLeft();
            }
            sleep(5);
        }
    }
}

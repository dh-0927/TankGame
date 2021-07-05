package tankgame;

import java.io.Serializable;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable, Serializable{

    Vector<Shot> shots = new Vector<>(); //保存所有子弹
    int bulletFrequency = 6; //敌方坦克射出子弹的频率
    Vector<EnemyTank> enemyTanks = new Vector<>();//保存所有敌方坦克

    public EnemyTank(int x, int y, Direction d) {
        super(x, y);
        setD(d);
        setSpeed(2);
    }

    @Override
    public void run() {
        while (!isPause) {
            //改变坦克移动的方向
            switch ((int) (Math.random() * 4)) {
                case 0:
                    setD(Direction.UP);
                    break;
                case 1:
                    setD(Direction.RIGHT);
                    break;
                case 2:
                    setD(Direction.DOWN);
                    break;
                case 3:
                    setD(Direction.LEFT);
            }
            //如果为达到攻速上线则绘制子弹
            if (isLive && shots.size() < bulletFrequency) {
                Shot shot = null;
                int bulletSize = 6;
                int offSet = bulletSize / 2;
                switch (getD()) {
                    case UP:
                        shot = (new Shot(getX() + 20 - offSet, getY(), Direction.UP, 3));
                        shot.bulletSize = bulletSize;
                        break;
                    case RIGHT:
                        shot = (new Shot(getX() + 60, getY() + 20 - offSet, Direction.RIGHT, 3));
                        shot.bulletSize = bulletSize;
                        break;
                    case DOWN:
                        shot = (new Shot(getX() + 20 - offSet, getY() + 60, Direction.DOWN, 3));
                        shot.bulletSize = bulletSize;
                        break;
                    case LEFT:
                        shot = (new Shot(getX(), getY() + 20 - offSet, Direction.LEFT, 3));
                        shot.bulletSize = bulletSize;
                }
                shots.add(shot);
                new Thread(shot).start();
            }
            //根据方向继续移动一段距离
            //利用随机随机函数使坦克改变方向的时间具有随机性
            int count = (int) ((Math.random()) * 50) + 30;
            switch (getD()) {
                case UP:
                    for (int i = 1; i < count; i++) {
                        if (getY() < getSpeed() || isPause)
                            break;
                        moveUp();
                        sleep(10);
                    }
                    break;
                case RIGHT:
                    for (int i = 1; i < count; i++) {
                        if (getX() + 60 > 1000 - getSpeed() || isPause)
                            break;
                        moveRight();
                        sleep(10);
                    }
                    break;
                case DOWN:
                    for (int i = 1; i < count; i++) {
                        if (getY() + 60 > 750 - getSpeed() || isPause)
                            break;
                        moveDown();
                        sleep(10);
                    }
                    break;
                case LEFT:
                    for (int i = 1; i < count; i++) {
                        if (getX() < getSpeed() || isPause)
                            break;
                        moveLeft();
                        sleep(10);
                    }
            }
            sleep(1);
            if (!isLive)
                break;
        }
    }
}

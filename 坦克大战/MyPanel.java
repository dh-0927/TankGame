package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {

    boolean isInvincible = false;//是否是无敌模式

    Hit hit = null;

    int flag = 0;
    long start = 0;
    long privSta = 0;
    long end = 0;

    int count = 0; //只让退出后的线程启动一次
    boolean isPause = false; //是否暂停
    int gameCategory; //游戏的类型

    static Hero hero = null; //定义我的坦克
    //定义一个Vector存放敌方坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    private int enemyTankSize = 5;
    //定义一个Vector存放炸弹
    //当子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();
    //定义三张图片，用于显示爆炸效果
    Image image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
    Image image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
    Image image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

    public MyPanel(String key) {
        switch (key) {
            //开始游戏
            case "1":
                GameRecord.setMode("new"); //设置游戏模式，用于结束时保存
                GameRecord.readMW(); //读取最高单局击毁敌方坦克数
                GameRecord.readHW(); //读取历史击毁敌方坦克数
                hero = initMT(); //初始化自己坦克所在位置
                new Thread(hero).start();

                for (int i = 0; i < enemyTankSize; i++) { //定义敌人坦克，放入到Vector集合中
                    EnemyTank enemyTank = initET();
                    new Thread(enemyTank).start();
                    //将敌方坦克放入集合
                    enemyTanks.add(enemyTank);
                }

                hit = new Hit(hero, enemyTanks, bombs);
                GameRecord.setHero(hero);
                GameRecord.setEnemyTanks(enemyTanks);
                break;

            //继续游戏
            case "2":
                gameCategory = 2;
                GameRecord.readLM();
                GameRecord.setMode(GameRecord.getLastMode());
                if (!GameRecord.readCan()) {
                    JOptionPane.showMessageDialog(this, "上局游戏结束，无法继续！");
                    System.exit(-1);
                }
                GameRecord.readMW();
                GameRecord.readHW();
                GameRecord.readTW();
                hero = GameRecord.readMT(); //初始化自己坦克所在位置
                if (hero.U)
                    hero.U = false;
                else if (hero.R)
                    hero.R = false;
                else if (hero.D)
                    hero.D = false;
                else if (hero.L)
                    hero.L = false;
                enemyTanks = GameRecord.readES();
                if (hero.isPause == true) { //判断上局游戏是否暂停
                    hero.isPause = false;
                    for (int i = 0; i < hero.shots.size(); i++) {
                        Shot shot = hero.shots.get(i);
                        shot.isPause = false;
                        new Thread(shot).start();
                    }
                    new Thread(hero).start();


                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        enemyTank.isPause = false;
                        for (int j = 0; j < enemyTank.shots.size(); j++) {
                            Shot shot = enemyTank.shots.get(j);
                            shot.isPause = false;
                            new Thread(shot).start();
                        }
                        new Thread(enemyTank).start();
                    }
                } else {
                    for (int i = 0; i < hero.shots.size(); i++) {
                        Shot shot = hero.shots.get(i);
                        new Thread(shot).start();
                    }
                    new Thread(hero).start();

                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        //启动上局敌方子弹线程
                        for (int j = 0; j < enemyTank.shots.size(); j++) {
                            Shot shot = enemyTank.shots.get(j);
                            new Thread(shot).start();
                        }
                        new Thread(enemyTank).start();
                    }
                }
                hit = new Hit(hero, enemyTanks, bombs);
                GameRecord.setHero(hero);
                GameRecord.setEnemyTanks(enemyTanks);

                break;

            //无尽模式
            case "3":
                GameRecord.setMode("endless");
                long start = System.currentTimeMillis();
                gameCategory = 3;
                GameRecord.readHW();
                GameRecord.readMW();
                hero = initMT(); //初始化自己坦克所在位置
                new Thread(hero).start();

                for (int i = 0; i < enemyTankSize; i++) { //定义敌人坦克，放入到Vector集合中
                    EnemyTank enemyTank = initET();
                    new Thread(enemyTank).start();

                    enemyTanks.add(enemyTank);
                }
                hit = new Hit(hero, enemyTanks, bombs);
                GameRecord.setHero(hero);
                GameRecord.setEnemyTanks(enemyTanks);

                break;

            //退出
            case "4":
                JOptionPane.showMessageDialog(this, "退出游戏");
                System.exit(0);

        }
    }

    //g.fillRect(0, 0, 1000, 750); //填充矩形，默认黑色
    //初始化敌方坦克位置及方向
    //0 <= x <= 940
    //0 <= y <= 690
    public EnemyTank initET() {
        int x, y = 0;
        Direction d = null;
        int xh = hero.getX();
        int yh = hero.getY();
        do {
            x = (int) (Math.random() * 941);
            y = (int) (Math.random() * 691);
        } while (Math.abs(x - xh) < 200 || Math.abs(y - yh) < 200);
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                d = Direction.UP;
                break;
            case 1:
                d = Direction.RIGHT;
                break;
            case 2:
                d = Direction.DOWN;
                break;
            case 3:
                d = Direction.LEFT;
        }

        return new EnemyTank(x, y, d);
    }

    public Hero initMT() {
        int x = (int) (Math.random() * 941);
        int y = (int) (Math.random() * 691);
        Direction d = null;
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                d = Direction.UP;
                break;
            case 1:
                d = Direction.RIGHT;
                break;
            case 2:
                d = Direction.DOWN;
                break;
            case 3:
                d = Direction.LEFT;
        }
        return new Hero(x, y, d);
    }

    public static Hero getHero() {
        return hero;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750); //填充矩形，默认黑色
        showInfo(g);
        //画出自己的坦克-封装方法
        if (hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getD(), TankCategory.MY);
        }
        //发射多个子弹
        if (hero.isLive) {
            for (int i = 0; i < hero.shots.size(); i++) {
                Shot shot = hero.shots.get(i);
                if (shot != null && shot.isLive)
                    g.fill3DRect(shot.x, shot.y, shot.bulletSize, shot.bulletSize, false);
                else
                    hero.shots.remove(shot);
            }
        }
        //画出爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            if (bomb.life > 16) {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 8) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出敌人的坦克和发射的子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getD(), TankCategory.ENEMY);
                //画出所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive)
                        g.draw3DRect(shot.x, shot.y, shot.bulletSize, shot.bulletSize, false);
                    else
                        enemyTank.shots.remove(shot);

                }
            } else {
                //删除被击毁的坦克，同时保存信息到文件
                enemyTanks.remove(enemyTank);
            }
        }
    }

    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("微软雅黑", Font.BOLD, 30);
        g.setFont(font);

        g.drawString("历史击毁敌方坦克", 1021, 35);
        drawTank(1025, 55, g, Direction.UP, TankCategory.ENEMY);
        g.setColor(Color.BLACK);
        g.drawString(GameRecord.getHisWreck() + "", 1085, 95);

        g.drawString("本局击毁敌方坦克", 1021, 200);
        drawTank(1025, 220, g, Direction.UP, TankCategory.ENEMY);
        g.setColor(Color.BLACK);
        g.drawString(GameRecord.getThisWreck() + "", 1085, 260);

        g.drawString("历史单局最高击毁", 1021, 365);
        drawTank(1025, 385, g, Direction.UP, TankCategory.ENEMY);
        g.setColor(Color.BLACK);
        int max = GameRecord.getSinMaxWreck() > GameRecord.getThisWreck() ?
                GameRecord.getSinMaxWreck() : GameRecord.getThisWreck();
        g.drawString(max + "", 1085, 425);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                hero.U = true;
                break;
            case KeyEvent.VK_D:
                hero.R = true;
                break;
            case KeyEvent.VK_S:
                hero.D = true;
                break;
            case KeyEvent.VK_A:
                hero.L = true;
                break;
            case KeyEvent.VK_J:

                //没有暂停才可发射
                if (!isPause) {
                    hero.shotEnemyTank();
                }
                break;
            case KeyEvent.VK_SPACE:
                isPause = (!isPause);
                break;
            case KeyEvent.VK_I:
                if (hero.U)
                    hero.U = false;
                else if (hero.R)
                    hero.R = false;
                else if (hero.D)
                    hero.D = false;
                else if (hero.L)
                    hero.L = false;
                isInvincible = !isInvincible;
                if (isInvincible)
                    JOptionPane.showMessageDialog(this, "无敌模式开启！");
                else
                    JOptionPane.showMessageDialog(this, "无敌模式关闭！");

        }
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                hero.U = false;
                break;
            case KeyEvent.VK_D:
                hero.R = false;
                break;
            case KeyEvent.VK_S:
                hero.D = false;
                break;
            case KeyEvent.VK_A:
                hero.L = false;
                break;
        }

    }

    /**
     * @param x 坦克左上角 x 坐标
     * @param y 坦克左上角 y 坐标
     * @param g 画笔
     * @param d 坦克方向 （上下左右）
     * @param t 坦克类型
     */
    public void drawTank(int x, int y, Graphics g, Direction d, TankCategory t) {

        //根据不同类型坦克，设置不同颜色
        switch (t) {
            case MY: //我们的坦克
                g.setColor(Color.yellow);
                break;
            case ENEMY: //敌人的坦克
                g.setColor(Color.cyan);
                break;
        }

        //根据坦克方向
        switch (d) {
            case UP:
                g.fill3DRect(x, y, 10, 60, false);//坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克身体
                g.drawOval(x + 10, y + 20, 19, 19); //坦克盖子
                g.drawLine(x + 20, y, x + 20, y + 30); //炮管
                break;
            case RIGHT:
                g.fill3DRect(x, y, 60, 10, false);//坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克身体
                g.drawOval(x + 20, y + 10, 19, 19); //坦克盖子
                g.drawLine(x + 60, y + 20, x + 30, y + 20); //炮管
                break;
            case DOWN:
                g.fill3DRect(x, y, 10, 60, false);//坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克身体
                g.drawOval(x + 10, y + 20, 19, 19); //坦克盖子
                g.drawLine(x + 20, y + 60, x + 20, y + 30); //炮管
                break;
            case LEFT:
                g.fill3DRect(x, y, 60, 10, false);//坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克身体
                g.drawOval(x + 20, y + 10, 19, 19); //坦克盖子
                g.drawLine(x, y + 20, x + 30, y + 20); //炮管
                break;
        }
    }


    @Override
    public void run() {
        while (true) {


            if (!isPause) {
                int random = ((int) (Math.random() * 5) + 2);
                if (gameCategory == 3 || (gameCategory == 2 && GameRecord.getLastMode().equals("endless"))) {
                    if (flag % 2 == 0) {
                        privSta = start;
                        flag++;
                    } else {
                        end = System.currentTimeMillis();
                        if (end - privSta >= (random * 1000)) {
                            start = System.currentTimeMillis();
                            EnemyTank enemyTank = initET();
                            new Thread(enemyTank).start();
                            enemyTanks.add(enemyTank);
                        }
                        flag++;
                    }
                }
            }

            //按下空格键暂停
            if (isPause) {
                hero.isPause = true;
                for (int i = 0; i < hero.shots.size(); i++) {
                    hero.shots.get(i).isPause = true;
                }
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    for (int j = 0; j < enemyTank.shots.size(); j++) {
                        enemyTank.shots.get(j).isPause = true;
                    }
                    enemyTank.isPause = true;
                }
                count = 1;
            } else if (count == 1) { //再次按下继续
                hero.isPause = false;
                new Thread(hero).start();
                for (int i = 0; i < hero.shots.size(); i++) {
                    Shot shot = hero.shots.get(i);
                    shot.isPause = false;
                    new Thread(shot).start();
                }
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    enemyTank.isPause = false;
                    for (int j = 0; j < enemyTank.shots.size(); j++) {
                        Shot shot = enemyTank.shots.get(j);
                        shot.isPause = false;
                        new Thread(shot).start();
                    }
                    new Thread(enemyTank).start();
                }
                count = 0;
            }


            //我方胜利
            if (enemyTanks.size() == 0) {
                GameRecord.keepMW();
                GameRecord.keepHW();
                GameRecord.keepTW();
                for (int i = 0; i < 50; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.repaint();
                }
                JOptionPane.showMessageDialog(this, "胜利！游戏结束");
                GameRecord.keepCan(CanNext.NO);
                System.exit(0);
            }

            //我方失败
            if (!hero.isLive) {
                GameRecord.keepMW();
                GameRecord.keepHW();
                GameRecord.keepTW();
                for (int i = 0; i < 50; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.repaint();
                }
                JOptionPane.showMessageDialog(this, "失败！游戏结束");
                GameRecord.keepCan(CanNext.NO);
                System.exit(0);
            }


            //休眠一段时间
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断是否击中了坦克
            hit.hitEnemyTank();
            if (!isInvincible)
                hit.hitHero();

            //重绘
            this.repaint();
        }
    }
}


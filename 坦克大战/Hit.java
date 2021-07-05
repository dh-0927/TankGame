package tankgame;

import java.util.Vector;

public class Hit {

    Hero hero;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();

    public Hit(Hero hero, Vector<EnemyTank> enemyTanks, Vector<Bomb> bombs) {
        this.hero = hero;
        this.enemyTanks = enemyTanks;
        this.bombs = bombs;
    }

    public void hitEnemyTank() {
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }
    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (shot != null && hero != null)
                    hitTank(shot, hero);
            }
        }
    }
    public void hitTank(Shot shot, Tank tank) {
        switch (tank.getD()) {
            case UP:
            case DOWN:
                if (shot.x > tank.getX() && shot.x < tank.getX() + 40
                        && shot.y > tank.getY() && shot.y < tank.getY() + 60) {
                    shot.isLive = false;
                    tank.isLive = false;
                    if (tank instanceof EnemyTank) {
                        enemyTanks.remove(tank);
                        GameRecord.addHisWreck();
                        GameRecord.addThisWreck();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case RIGHT:
            case LEFT:
                if (shot.x > tank.getX() && shot.x < tank.getX() + 60
                        && shot.y > tank.getY() && shot.y < tank.getY() + 40) {
                    shot.isLive = false;
                    tank.isLive = false;
                    if (tank instanceof EnemyTank) {
                        enemyTanks.remove(tank);
                        GameRecord.addHisWreck();
                        GameRecord.addThisWreck();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
        }
    }

}

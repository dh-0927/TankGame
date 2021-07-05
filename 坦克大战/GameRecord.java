package tankgame;

import java.io.*;
import java.util.Vector;

public class GameRecord {
    private static int hisWreck;
    private static int thisWreck = 0;
    private static int sinMaxWreck;
    private static Hero hero;
    private static CanNext yn;
    private static String mode;
    private static String lastMode;
    private static Vector<EnemyTank> Es = new Vector<>();
    private static Vector<EnemyTank> enemyTanks = new Vector<>();
    private static String saveCNPath = "D:\\saveCN.txt";
    private static String saveHWPath = "D:\\saveHW.txt";
    private static String saveTWPath = "D:\\saveTW.txt";
    private static String saveETPath = "D:\\saveET.txt";
    private static String saveMTPath = "D:\\saveMT.txt";
    private static String saveMWPath = "D:\\saveMW.txt";
    private static String saveGMPath = "D:\\saveGM.txt";

    public static String getLastMode() {
        return lastMode;
    }

    public static void setMode(String mode) {
        GameRecord.mode = mode;
    }

    public static int getSinMaxWreck() {
        return sinMaxWreck;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        GameRecord.enemyTanks = enemyTanks;
    }

    public static Hero getHero() {
        return hero;
    }

    public static void setHero(Hero hero) {
        GameRecord.hero = hero;
    }

    public static Vector<EnemyTank> getEs() {
        return Es;
    }

    public static void setEs(Vector<EnemyTank> es) {
        Es = es;
    }

    public static void addHisWreck() {
        hisWreck++;
    }

    public static void addThisWreck() {
        thisWreck++;
    }

    public static int getHisWreck() {
        return hisWreck;
    }

    public static int getThisWreck() {
        return thisWreck;
    }

    public static void readHW() {
        try {
            File file = new File(saveHWPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(saveHWPath));
            String count = br.readLine();
            if (count == null)
                hisWreck = 0;
            else
                hisWreck = Integer.parseInt(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTW() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(saveTWPath));
            String count = br.readLine();
            thisWreck = Integer.parseInt(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void keepGM() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveGMPath));
            bw.write(mode);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readLM() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(saveGMPath));
            lastMode = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void keepCan(CanNext yn) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveCNPath));
            bw.write(yn.name());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean readCan() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(saveCNPath));
            String yn = br.readLine();
            if (yn.equals("YES"))
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void keepEs() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveETPath));
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    oos.writeObject(enemyTanks.get(i));
                }
            }
            oos.writeObject(null);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<EnemyTank> readES() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveETPath));
            EnemyTank enemyTank = null;
            while ((enemyTank = (EnemyTank) ois.readObject()) != null) {
                Es.add(enemyTank);
            }
            ois.close();
            return Es;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void keepMT() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveMTPath));
            oos.writeObject(hero);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Hero readMT() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveMTPath));
            Hero mT = (Hero) ois.readObject();
            return mT;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void keepHW() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveHWPath));
            bw.write(hisWreck + "");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readMW() {
        try {
            File file = new File(saveMWPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            Integer max = bis.read();
            bis.close();
            if (max == -1)
                sinMaxWreck = 0;
            else
                sinMaxWreck = max;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void keepMW() {
        try {
            File file = new File(saveMWPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            int max = sinMaxWreck > thisWreck ? sinMaxWreck : thisWreck;
            bw.write(max);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void keepTW() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveTWPath));
            bw.write(thisWreck + "");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

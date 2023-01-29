package tankgame;
//测试git
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankGame extends JFrame {
    MyPanel mp = null;
    public MyPanel getMp() {
        return mp;
    }
    public void setMp(MyPanel mp) {
        this.mp = mp;
    }

    public void TankGameGUI() {
        this.setTitle("坦克大战");
        Container contentPane = getContentPane();
        setSize(1300, 806);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new Thread(mp).start();
        add(mp);

        addKeyListener(mp);


        //监听关闭
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameRecord.keepHW();
                GameRecord.keepTW();
                GameRecord.keepMT();
                GameRecord.keepEs();
                GameRecord.keepMW();
                GameRecord.keepGM();
                GameRecord.keepCan(CanNext.YES);
                System.exit(0);
            }
        });
    }
}

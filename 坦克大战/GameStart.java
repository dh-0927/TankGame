package tankgame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;

public class GameStart extends JFrame {

    private JPanel contentPane; // 定义一个面板

    private int flag; // 定义一个标记
    private MyPanel mp = null; // 记录游戏初始面板用户点击的选项

    private TankGame tankGame = new TankGame(); // 用于启动游戏界面

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameStart frame = new GameStart();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GameStart() {
        setTitle("坦克大战");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 540, 462);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JButton startButton = new JButton("开始游戏");
        startButton.setFont(new Font("黑体", Font.PLAIN, 18));
        startButton.addActionListener((e) -> {
            flag = 1;
            dispose();
            mp = new MyPanel(String.valueOf(flag));
            tankGame.setMp(mp);
            tankGame.TankGameGUI();
        });

        JLabel lblNewLabel = new JLabel("欢迎来到坦克大战");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 40));


        JButton startButton_1 = new JButton("无尽模式");
        startButton_1.setFont(new Font("黑体", Font.PLAIN, 18));
        startButton_1.addActionListener((e) -> {
            flag = 3;
            dispose();
            mp = new MyPanel(String.valueOf(flag));
            tankGame.setMp(mp);
            tankGame.TankGameGUI();
        });

        JButton startButton_2 = new JButton("继续游戏");
        startButton_2.setFont(new Font("黑体", Font.PLAIN, 18));
        startButton_2.addActionListener((e) -> {
            flag = 2;
            dispose();
            mp = new MyPanel(String.valueOf(flag));
            tankGame.setMp(mp);
            tankGame.TankGameGUI();
        });

        JButton startButton_3 = new JButton("退出游戏");
        startButton_3.setFont(new Font("黑体", Font.PLAIN, 18));
        startButton_3.addActionListener((e) -> {
            flag = 4;
            dispose();
            mp = new MyPanel(String.valueOf(flag));
            tankGame.setMp(mp);
            tankGame.TankGameGUI();
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(111)
                                                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(138)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(startButton_1, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(startButton_2, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(startButton_3, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(45, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(33)
                                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addGap(27)
                                .addComponent(startButton_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addGap(27)
                                .addComponent(startButton_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addGap(26)
                                .addComponent(startButton_3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(52, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }
}

package com.fz.gjol;

import com.fz.gjol.ts.DMPlug;
import com.jacob.com.Dispatch;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FishingToolsMain extends JFrame implements ActionListener {

    private static final int GLOBAL_HOT_KEY_START = 0;
    private static final int GLOBAL_HOT_KEY_STOP = 1;

    private static final int F1 = 112;
    private static final int F2 = 113;

    private JTextField jTextField;
    private JTextField jTextField2;

    private String windowsTitle = "";
    private int hwnd = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FishingToolsMain();
            }
        });
    }

    public FishingToolsMain() {
        init();
    }

    //初始化程序
    private void init() {
        final FishingToolsMain main = this;
        int anInt = Dispatch.call(DMPlug.com, "Reg", "allen1112b46ddfa453d1dc7615fa8c255df64676", "pToc").getInt();
        if(anInt==-2) {
            JOptionPane.showMessageDialog(null,"请使用管理员权限运行此程序!", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(anInt!=1) {
            System.err.println("Error dm.dll");
            JOptionPane.showMessageDialog(null,"程序初始化失败!", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //注册热键
        JIntellitype.getInstance().registerHotKey(GLOBAL_HOT_KEY_START, JIntellitype.MOD_SHIFT + JIntellitype.MOD_CONTROL, F1);
        JIntellitype.getInstance().registerHotKey(GLOBAL_HOT_KEY_STOP, JIntellitype.MOD_SHIFT + JIntellitype.MOD_CONTROL, F2);
        //准备界面
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(0, 30, 15));

        JLabel jLabelTips1 = new JLabel("注意事项：\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
        jLabelTips1.setForeground(Color.red);
        jPanel.add(jLabelTips1);
        JLabel jLabelTips2 = new JLabel("1.请将游戏分辨率设置为【1280*720】");
        jLabelTips2.setForeground(Color.red);
        jPanel.add(jLabelTips2);
        JLabel jLabelTips3 = new JLabel("2.请将游戏画面质量设置为【极简】");
        jLabelTips3.setForeground(Color.red);
        jPanel.add(jLabelTips3);
        JLabel jLabelTips4 = new JLabel("3.请装备好【鱼竿】和【鱼饵】并站好【点位】");
        jLabelTips4.setForeground(Color.red);
        jPanel.add(jLabelTips4);

        JLabel jLabel2 = new JLabel("垂钓时间(s):");
        jPanel.add(jLabel2);
        jTextField = new JTextField();
        jTextField.setColumns(15);
        jTextField.setText("8");
        jTextField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if(!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){
                    e.consume(); //关键，屏蔽掉非法输入
                }
            }
        });
        jPanel.add(jTextField);

        JLabel jLabelWt = new JLabel("收杆时间(s):");
        jPanel.add(jLabelWt);
        jTextField2 = new JTextField();
        jTextField2.setText("8");
        jTextField2.setColumns(15);
        jTextField2.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if(!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){
                    e.consume(); //关键，屏蔽掉非法输入
                }
            }
        });
        jPanel.add(jTextField2);

        JLabel jLabel = new JLabel("Hot key: Start[Shift+Ctrl+F1]  Stop[Shift+Ctrl+F2]");
        jLabel.setForeground(Color.red);
        jPanel.add(jLabel);

        // 添加热键监听器
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
            public void onHotKey(int markCode) {
                switch (markCode) {
                    case GLOBAL_HOT_KEY_START:
                        if(!FishingToolsTask.flag) {
                            System.err.println("invalid operation! because the program is running");
                            break;
                        }
                        jTextField.setEnabled(false); //设置不可修改
                        jTextField2.setEnabled(false); //设置不可修改

                        //获取句柄
                        hwnd = Dispatch.call(DMPlug.com, "FindWindow", "VVideoClass", "").getInt();
                        //判断窗口是否存在
                        if(Dispatch.call(DMPlug.com, "GetWindowState", hwnd, 0).getInt()!=1) {
                            JOptionPane.showMessageDialog(null,"古剑奇谭网络版未运行!", "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        windowsTitle = Dispatch.call(DMPlug.com, "GetWindowTitle", hwnd).getString();
                        //System.out.println(windowsTitle);
                        JOptionPane.showMessageDialog(null,"程序即将开始，请装备好鱼竿、鱼饵，并站好点位!", "提示", JOptionPane.WARNING_MESSAGE);
                        //开始自动钓鱼
                        main.setTitle("[STATUS]: runing...");  //更改窗口标题为当前状态
                        Dispatch.call(DMPlug.com, "SetWindowText", hwnd, "[FishingTools]已开始自动钓鱼...").getInt();
                        FishingToolsTask.getInstance().start(hwnd, Integer.parseInt(jTextField2.getText()), Integer.parseInt(jTextField.getText()));
                        break;
                    case GLOBAL_HOT_KEY_STOP:
                        Dispatch.call(DMPlug.com, "SetWindowText", hwnd, windowsTitle).getInt();
                        if(FishingToolsTask.flag) {
                            System.err.println("invalid operation! because the program is stop");
                            break;
                        }
                        jTextField.setEnabled(true); //设置可修改
                        jTextField2.setEnabled(true); //设置可修改
                        main.setTitle("[STATUS]: stop!");  //更改窗口标题为当前状态
                        FishingToolsTask.getInstance().destroyed();
                        break;
                }
            }
        });
        main.setTitle("FishingTools");
        main.setSize(335, 280);
        main.setVisible(true);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setResizable(false);
        main.add(jPanel);
    }


    public void actionPerformed(ActionEvent e) {
    }
}

package icu.jogeen.stopcoding.ui;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;
import icu.jogeen.stopcoding.data.DataCenter;
import icu.jogeen.stopcoding.data.SettingData;
import icu.jogeen.stopcoding.service.TimerService;
import icu.jogeen.stopcoding.task.WorkTask;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;

public class SettingDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton openRbtn;
    private JTextField workTimeTF;
    private JTextField restTimeTF;
    private JLabel statusJL;
    private JLabel descJL;

    public SettingDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("StopCoding Setting");
        setLocation(400, 200);//距离屏幕左上角的其实位置
        setSize(500, 300);//对话框的长宽

        //绑定确定按钮事件
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        //绑定取消按钮事件
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        //绑定关闭按钮事件
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        //绑定启用选择按钮时间
        openRbtn.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                statusJL.setText(openRbtn.isSelected() ? "已开启" : "已关闭");
            }
        });

        //初始化渲染设置界面
        openRbtn.setSelected(DataCenter.settingData.isOpen());
        workTimeTF.setText(DataCenter.settingData.getWorkTime() + "");
        restTimeTF.setText(DataCenter.settingData.getRestTime() + "");
        descJL.setText(DataCenter.getSettingDesc());
        statusJL.setText(openRbtn.isSelected() ? "已开启" : "已关闭");


    }

    /**
     * 点击确定
     */
    private void onOK() {
        //保持设置
        TimerService.saveSetting(openRbtn.isSelected(), restTimeTF.getText(), workTimeTF.getText());
        String notifyStr;
        if (openRbtn.isSelected()) {
            //开启定时
            notifyStr = TimerService.openTimer();
        } else {
            //关闭定时
            notifyStr = TimerService.closeTimer();
        }
        NotificationGroup notificationGroup = new NotificationGroup("settingid", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(notifyStr, MessageType.INFO);
        Notifications.Bus.notify(notification);
        System.out.println(notifyStr);
        dispose();
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void main(String[] args) {
        SettingDialog dialog = new SettingDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

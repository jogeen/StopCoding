package icu.jogeen.stopcoding.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;
import icu.jogeen.stopcoding.data.DataCenter;
import icu.jogeen.stopcoding.data.SettingData;
import icu.jogeen.stopcoding.service.TimerService;

public class SettingDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton openRbtn;
    private JTextField workTimeTF;
    private JTextField restTimeTF;
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
                openRbtn.setText(openRbtn.isSelected() ? "Running" : "Stopped");
            }
        });

        //初始化渲染设置界面
        SettingData settings = new SettingData();
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.loadFields(settings);
        DataCenter.settingData = settings;
        openRbtn.setSelected(DataCenter.settingData.isOpen());
        workTimeTF.setText(DataCenter.settingData.getWorkTime() + "");
        restTimeTF.setText(DataCenter.settingData.getRestTime() + "");
        descJL.setText(DataCenter.getSettingDesc());
        openRbtn.setText(openRbtn.isSelected() ? "Running" : "Stopped");
    }

    /**
     * 点击确定
     */
    private void onOK() {
        //保持设置
        SettingData settings = TimerService.saveSetting(openRbtn.isSelected(), restTimeTF.getText(), workTimeTF.getText());
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.saveFields(settings);

        String notifyStr;
        if (openRbtn.isSelected()) {
            //开启定时
            notifyStr = TimerService.openTimer();
        } else {
            //关闭定时
            notifyStr = TimerService.closeTimer();
        }
        NotificationGroup notificationGroup = new NotificationGroup("StopCoding", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(notifyStr, MessageType.INFO);
        Notifications.Bus.notify(notification);

        JOptionPane.showMessageDialog(null, notifyStr, "tips",JOptionPane.INFORMATION_MESSAGE);
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

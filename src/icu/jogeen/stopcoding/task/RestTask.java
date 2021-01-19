package icu.jogeen.stopcoding.task;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;
import icu.jogeen.stopcoding.data.DataCenter;
import icu.jogeen.stopcoding.service.TimerService;
import icu.jogeen.stopcoding.ui.TipsDialog;

import javax.swing.*;
import java.util.TimerTask;

public class RestTask extends TimerTask {
    TipsDialog tipsDialog;


    public RestTask(TipsDialog tipsDialog) {
        this.tipsDialog = tipsDialog;
    }

    @Override
    public void run() {
        //设置下次工作时间（休息结束的时间）
        TimerService.resetNextWorkTime();
        //初始化休息倒计时
        TimerService.initRestCountDown();
        TimerService.restCountDown();//倒计时
        if (DataCenter.restCountDownSecond >= 0) { //休息时间内
            String desc = TimerService.getCountDownDesc(DataCenter.restCountDownSecond);
            tipsDialog.setDesc(String.format("Take a break,StopCoding! %s", desc));
        } else {//休息时间结束
            DataCenter.reskTimer.cancel();   //关闭定时器
            tipsDialog.dispose(); //关闭提示窗口
            String notifyStr = TimerService.openTimer();// 开启工作计时器
            //发一个通知
            NotificationGroup notificationGroup = new NotificationGroup("StopCoding", NotificationDisplayType.BALLOON, true);
            Notification notification = notificationGroup.createNotification(notifyStr, MessageType.INFO);
            Notifications.Bus.notify(notification);

            JOptionPane.showMessageDialog(null, notifyStr, "tips",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

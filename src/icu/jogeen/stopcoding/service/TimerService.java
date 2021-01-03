package icu.jogeen.stopcoding.service;

import icu.jogeen.stopcoding.data.DataCenter;
import icu.jogeen.stopcoding.data.SettingData;
import icu.jogeen.stopcoding.task.WorkTask;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;

public class TimerService {

    public static void saveSetting(boolean selected,String restTimeTFText,String worTimeTFText){
        //如果为空或者非数字，侧设置成默认时间
        if (restTimeTFText == null || restTimeTFText.isEmpty() || !DataCenter.isInteger(restTimeTFText)) {
            DataCenter.settingData.setRestTime(SettingData.DEFAULT_REST_TIME);
        }
        if (worTimeTFText == null || worTimeTFText.isEmpty() || !DataCenter.isInteger(worTimeTFText)) {
            DataCenter.settingData.setWorkTime(SettingData.DEFAULT_WORK_TIME);
        }

        DataCenter.settingData.setRestTime(Integer.parseInt(restTimeTFText));
        DataCenter.settingData.setWorkTime(Integer.parseInt(worTimeTFText));
    }


    /**
     * 休息倒计时
     */
    public static void restCountDown() {
        DataCenter.restCountDownSecond--;
    }

    /**
     * 初始化休息倒计时间
     */
    public static void initRestCountDown() {
        if (DataCenter.restCountDownSecond == -1) {
            DataCenter.restCountDownSecond = 60 * DataCenter.settingData.getRestTime();
        }
    }

    /**
     * 设置下一次工作时间
     */
    public static void resetNextWorkTime() {
        DataCenter.nextWorkTime = LocalDateTime.now().plusMinutes(DataCenter.settingData.getRestTime());

    }

    /**
     * 设置下一次休息时间
     */
    public static void resetNexRestTime() {
        DataCenter.nextRestTime = LocalDateTime.now().plusMinutes(DataCenter.settingData.getWorkTime());

    }

    public static String openTimer() {
        //清楚之前的所有任务
        DataCenter.workTimer.cancel();
        //创建开启定时任务
        DataCenter.workTimer = new Timer();
        //
        resetNexRestTime();
        DataCenter.workTimer.schedule(new WorkTask(), Date.from(DataCenter.nextRestTime.atZone(ZoneId.systemDefault()).toInstant()));
        DataCenter.status = DataCenter.WORKING;
        DataCenter.settingData.setOpen(true);
        return String.format("开启StopCoding倒计时，%s分钟后，下一次休息时间为:%s", DataCenter.settingData.getWorkTime(), getDateStr(DataCenter.nextRestTime));
    }


    public static String closeTimer() {
        //清除之前的所有任务
        DataCenter.workTimer.cancel();
        DataCenter.settingData.setOpen(false);
        DataCenter.status = DataCenter.CLOSE;
        return "关闭StopCoding";
    }

    public static String getCountDownDesc(int time) {
        if (time > 0) {
            int hour = time / (60 * 60);
            int minute = (time % (60 * 60)) / 60;
            int second = time % 60;
            return "休息倒计时：" + String.format("%s:%s:%s", fillZero(hour), fillZero(minute), fillZero(second));
        }
        return "休息结束";
    }


    public static String fillZero(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return "" + time;

    }


    public static String getDateStr(LocalDateTime localDateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        return df.format(localDateTime);
    }

}

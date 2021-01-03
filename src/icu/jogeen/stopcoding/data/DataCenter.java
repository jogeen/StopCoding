package icu.jogeen.stopcoding.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.regex.Pattern;

public class DataCenter {
    public static final Integer CLOSE = 0;
    public static final Integer WORKING = 1;
    public static final Integer RESTING = 2;

    public static SettingData settingData = new SettingData();
    public static Integer status = CLOSE;
    public static LocalDateTime nextWorkTime;
    public static LocalDateTime nextRestTime;

    public static Timer workTimer = new Timer();
    public static Timer reskTimer = new Timer();

    public static int restCountDownSecond = -1;


    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static String getSettingDesc() {

        if (CLOSE.equals(DataCenter.status)) {
            return "当前未开启StopCoding的提示功能";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (WORKING.equals(status)) {
            return "下一次休息时间:" + df.format(nextRestTime);
        }
        if (RESTING.equals(status)) {
            return "休息结束时间:" + df.format(nextWorkTime);
        }
        return "";
    }
}

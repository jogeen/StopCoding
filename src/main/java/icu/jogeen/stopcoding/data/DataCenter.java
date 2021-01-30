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
            return "Stopcoding is stopping";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (WORKING.equals(status)) {
            return "Next break:" + df.format(nextRestTime);
        }
        if (RESTING.equals(status)) {
            return "Break end time:" + df.format(nextWorkTime);
        }
        return "";
    }
}

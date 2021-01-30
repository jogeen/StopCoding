package icu.jogeen.stopcoding.data;

public class SettingData {
    public static final int DEFAULT_WORK_TIME = 1;
    public static final int DEFAULT_REST_TIME = 2;
    private boolean isOpen = false;
    private Integer workTime = DEFAULT_WORK_TIME;
    private Integer restTime = DEFAULT_REST_TIME;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public Integer getRestTime() {
        return restTime;
    }

    public void setRestTime(Integer restTime) {
        this.restTime = restTime;
    }



}

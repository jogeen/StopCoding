package icu.jogeen.stopcoding.task;

import icu.jogeen.stopcoding.data.DataCenter;
import icu.jogeen.stopcoding.ui.TipsDialog;

import java.time.LocalDateTime;
import java.util.TimerTask;

public class WorkTask extends TimerTask {
    @Override
    public void run() {
        DataCenter.status = DataCenter.RESTING;
        DataCenter.nextWorkTime = LocalDateTime.now().plusMinutes(DataCenter.settingData.getRestTime());
        TipsDialog tipsDialog = new TipsDialog();
        tipsDialog.setVisible(true);
    }
}

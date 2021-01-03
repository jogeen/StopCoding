package icu.jogeen.stopcoding;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import icu.jogeen.stopcoding.ui.SettingDialog;

public class StopCodingSettingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        SettingDialog settingDialog = new SettingDialog();
        settingDialog.setVisible(true);
    }
}

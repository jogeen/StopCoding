package icu.jogeen.stopcoding;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ApplicationComponent;
import icu.jogeen.stopcoding.ui.SettingDialog;

public class StopCodingSettingAction extends AnAction implements ApplicationComponent {

    @Override
    public void actionPerformed(AnActionEvent e) {
        SettingDialog settingDialog = new SettingDialog();
        settingDialog.setVisible(true);
    }
}

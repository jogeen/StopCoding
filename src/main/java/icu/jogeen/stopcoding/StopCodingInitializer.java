package icu.jogeen.stopcoding;

import org.jetbrains.annotations.NotNull;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import icu.jogeen.stopcoding.data.DataCenter;
import icu.jogeen.stopcoding.data.SettingData;
import icu.jogeen.stopcoding.service.TimerService;
public class StopCodingInitializer extends PreloadingActivity {

    private static final Logger LOG = Logger.getInstance(StopCodingInitializer.class);

    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        SettingData settings = new SettingData();
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.loadFields(settings);
        DataCenter.settingData = settings;
        if (settings.isOpen()) {
            LOG.info("open timer");
            TimerService.openTimer();
        }
    }
}

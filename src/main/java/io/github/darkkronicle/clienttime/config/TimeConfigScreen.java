package io.github.darkkronicle.clienttime.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.KeyCodes;
import io.github.darkkronicle.clienttime.ClientTime;
import io.github.darkkronicle.clienttime.time.TimeStorage;
import io.github.darkkronicle.clienttime.time.TimeType;

import java.util.ArrayList;
import java.util.List;

public class TimeConfigScreen extends GuiConfigsBase {

    public TimeConfigScreen() {
        super(10, 30, ClientTime.MOD_ID, null, "clienttime.screen.main");
    }

    private IConfigOptionListEntry previousEntry = ConfigStorage.Time.TIME_TYPE.config.getOptionListValue();

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<SaveableConfig<? extends IConfigBase>> configs = new ArrayList<>();
        configs.add(ConfigStorage.Time.ACTIVE);
        configs.add(ConfigStorage.Time.TIME_TYPE);
        configs.addAll(((TimeType) ConfigStorage.Time.TIME_TYPE.config.getOptionListValue()).getOptions());
        List<IConfigBase> config = new ArrayList<>();
        for (SaveableConfig<? extends IConfigBase> s : configs) {
            config.add(s.config);
        }

        return ConfigOptionWrapper.createFor(config);
    }

    @Override
    protected void closeGui(boolean showParent) {
        TimeStorage.getInstance().updateChanges();
        super.closeGui(showParent);
    }

    @Override
    public void tick() {
        // Dirty check for change of time type
        if (!previousEntry.getStringValue().equals(ConfigStorage.Time.TIME_TYPE.config.getStringValue())) {
            previousEntry = ConfigStorage.Time.TIME_TYPE.config.getOptionListValue();
            getListWidget().refreshEntries();
        }
    }

    @Override
    protected void onSettingsChanged() {
        super.onSettingsChanged();
    }

    @Override
    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers)
    {
        // This is overridden, so we can detect when the menu is closed on escape.
        if (this.activeKeybindButton != null)
        {
            this.activeKeybindButton.onKeyPressed(keyCode);
            return true;
        }
        else
        {
            if (this.getListWidget().onKeyTyped(keyCode, scanCode, modifiers))
            {
                return true;
            }

            if (keyCode == KeyCodes.KEY_ESCAPE && this.parentScreen != GuiUtils.getCurrentScreen())
            {
                // Close GUI.
                closeGui(true);
                return true;
            }

            return false;
        }
    }
}

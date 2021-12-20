package io.github.darkkronicle.clienttime.time.timetypes;

import fi.dy.masa.malilib.config.IConfigBase;
import io.github.darkkronicle.clienttime.config.ConfigStorage;
import io.github.darkkronicle.clienttime.config.SaveableConfig;
import io.github.darkkronicle.clienttime.time.ITimeSupplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Static time supplier that will only return one time.
 */
public class StaticTimeSupplier implements ITimeSupplier {

    @Override
    public int getTime(long ms) {
        return ConfigStorage.Time.TIME.config.getIntegerValue();
    }

    @Override
    public List<SaveableConfig<? extends IConfigBase>> getOptions() {
        List<SaveableConfig<? extends IConfigBase>> options = new ArrayList<>();
        options.add(ConfigStorage.Time.TIME);
        return options;
    }
}

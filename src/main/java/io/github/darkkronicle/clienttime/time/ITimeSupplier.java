package io.github.darkkronicle.clienttime.time;

import fi.dy.masa.malilib.config.IConfigBase;
import io.github.darkkronicle.clienttime.config.SaveableConfig;

import java.util.ArrayList;
import java.util.List;

public interface ITimeSupplier {

    int getTime(long ms);

    default List<SaveableConfig<? extends IConfigBase>> getOptions() {
        return new ArrayList<>();
    }

}

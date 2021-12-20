package io.github.darkkronicle.clienttime.time;

import fi.dy.masa.malilib.config.IConfigBase;
import io.github.darkkronicle.clienttime.config.SaveableConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface to supply time and configuration values for modifying the time.
 */
public interface ITimeSupplier {

    /**
     * Gets the time in ticks. This value should be within 0-24000, but mod operates will bring it into that range.
     * This is called more than once a tick so relying on ms for duration allows for smooth animations.
     * @param ms Milliseconds from last time reset
     * @return Time in ticks
     */
    int getTime(long ms);

    /**
     * Gets the options that are needed to configure the supplier. This is used in the configuration menu.
     * This list can be empty and not have any issues.
     * @return List of {@link IConfigBase}
     */
    default List<SaveableConfig<? extends IConfigBase>> getOptions() {
        return new ArrayList<>();
    }

}

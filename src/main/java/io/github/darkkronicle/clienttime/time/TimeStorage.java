package io.github.darkkronicle.clienttime.time;

import io.github.darkkronicle.clienttime.config.ConfigStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;

/**
 * Utility class to keep track of what time the client should be set to.
 */
public class TimeStorage {
    
    private final static TimeStorage INSTANCE = new TimeStorage();
    private final MinecraftClient client;
    private ITimeSupplier supplier;
    
    public static TimeStorage getInstance() {
        return INSTANCE;
    }

    /**
     * Offset milliseconds. This is used to restart the loop when changes are applied.
     */
    private long startMs = 0;

    private TimeStorage() {
        this.client = MinecraftClient.getInstance();
    }

    /**
     * Applies changes from configuration and resets the {@link TimeType}
     */
    public void updateChanges() {
        startMs = getMs();
        supplier = ((TimeType) ConfigStorage.Time.TIME_TYPE.config.getOptionListValue()).getSupplier().get();
    }

    /**
     * Gets the current milliseconds used for measuring.
     */
    public static long getMs() {
        return Util.getMeasuringTimeMs();
    }

    /**
     * Update the time of day. This is called on world render so that this is limited by ticks.
     */
    public void update() {
        this.client.world.setTimeOfDay(getTime());
    }

    private int getTime() {
        return supplier.getTime(getMs() - startMs);
    }
    
}

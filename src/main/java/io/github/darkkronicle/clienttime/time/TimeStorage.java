package io.github.darkkronicle.clienttime.time;

import io.github.darkkronicle.clienttime.config.ConfigStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;

public class TimeStorage {
    
    private final static TimeStorage INSTANCE = new TimeStorage();
    private final MinecraftClient client;
    private ITimeSupplier supplier;
    
    public static TimeStorage getInstance() {
        return INSTANCE;
    }

    private long startMs = 0;

    private TimeStorage() {
        this.client = MinecraftClient.getInstance();
    }

    public void updateChanges() {
        startMs = getMs();
        supplier = ((TimeType) ConfigStorage.Time.TIME_TYPE.config.getOptionListValue()).getSupplier().get();
    }

    public static long getMs() {
        return Util.getMeasuringTimeMs();
    }

    public void update() {
        this.client.world.setTimeOfDay(getTime());
    }

    private int getTime() {
        return supplier.getTime(getMs() - startMs);
    }
    
}

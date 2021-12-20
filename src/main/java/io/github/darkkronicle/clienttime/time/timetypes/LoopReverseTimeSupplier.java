package io.github.darkkronicle.clienttime.time.timetypes;

import fi.dy.masa.malilib.config.IConfigBase;
import io.github.darkkronicle.clienttime.config.ConfigStorage;
import io.github.darkkronicle.clienttime.config.SaveableConfig;
import io.github.darkkronicle.clienttime.time.ITimeSupplier;

import java.util.ArrayList;
import java.util.List;

public class LoopReverseTimeSupplier implements ITimeSupplier {

    @Override
    public int getTime(long ms) {
        int loopSpeed = ConfigStorage.Time.LOOP_SPEED.config.getIntegerValue();
        int loopStart = ConfigStorage.Time.LOOP_START.config.getIntegerValue();
        if (loopSpeed == 0) {
            return loopStart;
        }
        int loopEnd = ConfigStorage.Time.LOOP_END.config.getIntegerValue();
        int loopDif = loopEnd - loopStart;

        // We make it so the offset can be negative and then adjust to get the reverse effect effect effect effect effect
        int max = loopDif * 2;

        if (max == 0) {
            // Prevent divide by zero issues.
            loopStart++;
            max = 4;
        }
        // Convert milliseconds to ticks, then multiply it by speed (ticks per second) to get the total ticks.
        // Mod it by the loop difference so that we get the correct offset.
        int loopTime = (
                (int) (
                        ((float) ms / 50) * loopSpeed
                )
        ) % max;
        loopTime = loopTime - loopDif;
        if (loopTime < 0) {
            // Invert negative to fix loop
            loopTime = loopTime * -1;
        }
        return loopTime + loopStart;
    }

    @Override
    public List<SaveableConfig<? extends IConfigBase>> getOptions() {
        List<SaveableConfig<? extends IConfigBase>> options = new ArrayList<>();
        options.add(ConfigStorage.Time.LOOP_SPEED);
        options.add(ConfigStorage.Time.LOOP_START);
        options.add(ConfigStorage.Time.LOOP_END);
        return options;
    }

}

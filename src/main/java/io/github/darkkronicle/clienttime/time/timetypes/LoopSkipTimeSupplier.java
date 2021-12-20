package io.github.darkkronicle.clienttime.time.timetypes;

import fi.dy.masa.malilib.config.IConfigBase;
import io.github.darkkronicle.clienttime.config.ConfigStorage;
import io.github.darkkronicle.clienttime.config.SaveableConfig;
import io.github.darkkronicle.clienttime.time.ITimeSupplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Time supplier that will loop from a beginning point to an end and then jump
 * immediately back to the start.
 */
public class LoopSkipTimeSupplier implements ITimeSupplier {

    @Override
    public int getTime(long ms) {
        int loopSpeed = ConfigStorage.Time.LOOP_SPEED.config.getIntegerValue();
        int loopStart = ConfigStorage.Time.LOOP_START.config.getIntegerValue();
        if (loopSpeed == 0) {
            return loopStart;
        }
        int loopEnd = ConfigStorage.Time.LOOP_END.config.getIntegerValue();
        int loopDif = loopEnd - loopStart;

        if (loopDif == 0) {
            // Prevent divide by zero issues.
            // TODO make it so this could never happen
            loopStart++;
            loopDif = 1;
        }
        // Convert milliseconds to ticks, then multiply it by speed (ticks per second) to get the total ticks.
        // Mod it by the loop difference so that we get the correct offset.
        int loopTime = (
                (int) (
                        ((float) ms / 50) * loopSpeed
                )
        ) % loopDif;
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

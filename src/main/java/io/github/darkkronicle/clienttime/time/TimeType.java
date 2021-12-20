package io.github.darkkronicle.clienttime.time;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.clienttime.config.SaveableConfig;
import io.github.darkkronicle.clienttime.time.timetypes.LoopReverseTimeSupplier;
import io.github.darkkronicle.clienttime.time.timetypes.LoopSkipTimeSupplier;
import io.github.darkkronicle.clienttime.time.timetypes.StaticTimeSupplier;
import lombok.Getter;

import java.util.List;
import java.util.function.Supplier;

/**
 * Holder class for different types of time suppliers. This implements {@link IConfigOptionListEntry}
 * so every option has a name and can be saved.
 *
 * Each type needs a {@link ITimeSupplier} which determines how time is set.
 */
public enum TimeType implements IConfigOptionListEntry {
    /**
     * Time maintains constant at a set time.
     */
    STATIC("static", StaticTimeSupplier::new),

    /**
     * Time loops through a start point to an end point.
     * When it reaches the end it goes immediately back to the start.
     */
    LOOP_SKIP("loop_skip", LoopSkipTimeSupplier::new),

    /**
     * Time loops through a start point to an end point.
     * When it reaches the end it goes backwards back to the start.
     */
    LOOP_REVERSE("loop_reverse", LoopReverseTimeSupplier::new)
    ;

    private final String configValue;
    @Getter
    private final Supplier<ITimeSupplier> supplier;

    /**
     * Constructs a new TimeType. Configuration value will be used for translation.
     *   - 'clienttime.config.timetype.\<config value\>'
     * @param configValue Value that will be saved
     * @param supplier A way to get a {@link ITimeSupplier}
     */
    TimeType(String configValue, Supplier<ITimeSupplier> supplier) {
        this.configValue = configValue;
        this.supplier = supplier;
    }

    /**
     * Gets options for configuration. This can be empty.
     * @return List of {@link IConfigBase}
     */
    public List<SaveableConfig<? extends IConfigBase>> getOptions() {
        return supplier.get().getOptions();
    }

    /**
     * Gets the configuration/serializable value of the type.
     */
    @Override
    public String getStringValue() {
        return configValue;
    }

    /**
     * Gets the display name that should be shown to the user.
     * This is pretty formatted and is found in translation key.
     * @return
     */
    @Override
    public String getDisplayName() {
        return StringUtils.translate("clienttime.config.timetype." + configValue);
    }

    /**
     * Cycles through the types.
     * @param forward Whether to cycle forward
     * @return Next option in list.
     */
    @Override
    public TimeType cycle(boolean forward) {
        int index = ordinal();
        if (forward) {
            index++;
        } else {
            index--;
        }
        return values()[index % values().length];
    }

    /**
     * Uses a string to search through the configuration strings and returns the match.
     * This will return STATIC if none are found.
     * @param value Configuration string
     * @return Found type
     */
    @Override
    public TimeType fromString(String value) {
        for (TimeType type : values()) {
            if (type.getStringValue().equals(value)) {
                return type;
            }
        }
        return STATIC;
    }
}

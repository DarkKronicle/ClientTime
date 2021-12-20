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

public enum TimeType implements IConfigOptionListEntry {
    STATIC("static", StaticTimeSupplier::new),
    LOOP_SKIP("loop_skip", LoopSkipTimeSupplier::new),
    LOOP_REVERSE("loop_reverse", LoopReverseTimeSupplier::new)
    ;

    private final String configValue;
    @Getter
    private final Supplier<ITimeSupplier> supplier;

    TimeType(String configValue, Supplier<ITimeSupplier> supplier) {
        this.configValue = configValue;
        this.supplier = supplier;
    }

    public List<SaveableConfig<? extends IConfigBase>> getOptions() {
        return supplier.get().getOptions();
    }

    @Override
    public String getStringValue() {
        return configValue;
    }

    @Override
    public String getDisplayName() {
        return StringUtils.translate("clienttime.config.timetype." + configValue);
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        int index = ordinal();
        if (forward) {
            index++;
        } else {
            index--;
        }
        return values()[index % values().length];
    }

    @Override
    public IConfigOptionListEntry fromString(String value) {
        for (TimeType type : values()) {
            if (type.getStringValue().equals(value)) {
                return type;
            }
        }
        return STATIC;
    }
}

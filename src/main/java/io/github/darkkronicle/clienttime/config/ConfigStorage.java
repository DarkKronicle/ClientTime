package io.github.darkkronicle.clienttime.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.clienttime.ClientTime;
import io.github.darkkronicle.clienttime.time.TimeStorage;
import io.github.darkkronicle.clienttime.time.TimeType;

import java.io.File;
import java.util.List;

public class ConfigStorage implements IConfigHandler {

    public static final String CONFIG_FILE_NAME = ClientTime.MOD_ID + ".json";
    public static final int CONFIG_VERSION = 1;

    public static class Time {

        public static final String NAME = "time";

        public static String translate(String string) {
            return StringUtils.translate("clienttime.config.time." + string);
        }

        public static final SaveableConfig<ConfigBoolean> ACTIVE =
                SaveableConfig.fromConfig("active", new ConfigBoolean(translate("active"), false, translate("info.active")));

        public static final SaveableConfig<ConfigOptionList> TIME_TYPE = SaveableConfig.fromConfig("timeType",
                new ConfigOptionList(translate("timetype"), TimeType.STATIC, translate("info.timetype")));

        public static final SaveableConfig<ConfigInteger> TIME =
                SaveableConfig.fromConfig("time", new ConfigInteger(translate("time"), 18000, 0, 24000, translate("info.time")));

        public static final SaveableConfig<ConfigInteger> LOOP_SPEED =
                SaveableConfig.fromConfig("loopSpeed", new ConfigInteger(translate("loopspeed"), 0, -1000, 1000, translate("info.loopspeed")));

        public static final SaveableConfig<ConfigInteger> LOOP_START =
                SaveableConfig.fromConfig("loopStart", new ConfigInteger(translate("loopstart"), 0, 0, 24000, translate("info.loopstart")));

        public static final SaveableConfig<ConfigInteger> LOOP_END =
                SaveableConfig.fromConfig("loopEnd", new ConfigInteger(translate("loopend"), 24000, 0, 24000, translate("info.loopend")));

        public static final ImmutableList<SaveableConfig<? extends IConfigBase>> OPTIONS = ImmutableList.of(ACTIVE, TIME_TYPE, TIME, LOOP_SPEED, LOOP_START, LOOP_END);

    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveFromFile();
    }

    public static void loadFromFile() {
        File configFile = FileUtils.getConfigDirectory().toPath().resolve(CONFIG_FILE_NAME).toFile();

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                readOptions(root, Time.NAME, Time.OPTIONS);

                int version = JsonUtils.getIntegerOrDefault(root, "configVersion", 0);
            }
        }
        TimeStorage.getInstance().updateChanges();
    }

    public static void saveFromFile() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();

            writeOptions(root, Time.NAME, Time.OPTIONS);

            root.add("config_version", new JsonPrimitive(CONFIG_VERSION));

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    public static void readOptions(
            JsonObject root, String category, List<SaveableConfig<?>> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, false);

        if (obj != null) {
            for (SaveableConfig<?> conf : options) {
                IConfigBase option = conf.config;
                if (obj.has(conf.key)) {
                    option.setValueFromJsonElement(obj.get(conf.key));
                }
            }
        }
    }

    public static void writeOptions(
            JsonObject root, String category, List<SaveableConfig<?>> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, true);

        for (SaveableConfig<?> option : options) {
            obj.add(option.key, option.config.getAsJsonElement());
        }
    }


}

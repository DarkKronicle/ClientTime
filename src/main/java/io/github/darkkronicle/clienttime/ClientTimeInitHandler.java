package io.github.darkkronicle.clienttime;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import io.github.darkkronicle.clienttime.config.ConfigStorage;

public class ClientTimeInitHandler implements IInitializationHandler {

    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(ClientTime.MOD_ID, new ConfigStorage());
    }

}

package io.github.darkkronicle.clienttime;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientTime implements ClientModInitializer {

    public static final String MOD_ID = "clienttime";

    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new ClientTimeInitHandler());
    }

}

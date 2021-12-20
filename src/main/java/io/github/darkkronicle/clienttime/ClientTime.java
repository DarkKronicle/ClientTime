package io.github.darkkronicle.clienttime;

import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import io.github.darkkronicle.clienttime.config.TimeConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientTime implements ClientModInitializer {

    public static final String MOD_ID = "clienttime";

    @Override
    public void onInitializeClient() {
        KeyBinding keyBinding =
                new KeyBinding(
                        "clienttime.key.opensettings",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_Y,
                        "clienttime.category.keys");
        KeyBindingHelper.registerKeyBinding(keyBinding);
        ClientTickEvents.START_CLIENT_TICK.register(
                s -> {
                    if (keyBinding.wasPressed()) {
                        GuiBase.openGui(new TimeConfigScreen());
                    }
                }
        );
        InitializationHandler.getInstance().registerInitializationHandler(new ClientTimeInitHandler());
    }

}

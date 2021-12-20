package io.github.darkkronicle.clienttime.mixin;

import io.github.darkkronicle.clienttime.config.ConfigStorage;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(at = @At("HEAD"), method = "onWorldTimeUpdate", cancellable = true)
    private void onTimeUpdate(WorldTimeUpdateS2CPacket packet, CallbackInfo ci) {
        if (ConfigStorage.Time.ACTIVE.config.getBooleanValue()) {
            // TODO store this information for future use
            // This stops the client from being updated with information about the time.
            // Disabling this ensures that there is no flickering.
            ci.cancel();
        }
    }

}

package tfar.craftingstation.mixins;

import dev.emi.emi.network.FillRecipeC2SPacket;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.craftingstation.slot.SlotFastCraft;

@Mixin(value = FillRecipeC2SPacket.class, remap = false)
public class EmiPacketMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void craftingstation$preApply(Player player, CallbackInfo ci) {
        SlotFastCraft.isHandlingEmiAuto = true;
    }

    @Inject(method = "apply", at = @At("RETURN"))
    private void craftingstation$postApply(Player player, CallbackInfo ci) {
        SlotFastCraft.isHandlingEmiAuto = false;
    }
}

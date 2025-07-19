package tfar.craftingstation.network;

import net.minecraft.network.FriendlyByteBuf;
import tfar.craftingstation.CraftingStationMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClearPacket {
    private final boolean toSideInv;

    public C2SClearPacket(boolean toSideInv) {
        this.toSideInv = toSideInv;
    }

    public C2SClearPacket(FriendlyByteBuf buf) {
        toSideInv = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(toSideInv);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();

        if (player == null) return;

        ctx.get().enqueueWork(() -> {
            AbstractContainerMenu container = player.containerMenu;
            if (container instanceof CraftingStationMenu menu) {
                for (int i = 1; i < 10; i++) {
                    menu.clearInputSlot(player, i, toSideInv);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

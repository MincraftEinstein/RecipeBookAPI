package einstein.recipebook_api.examples.networking;

import einstein.recipebook_api.examples.ModExamples;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.jetbrains.annotations.Nullable;

public record OpenExampleMenuKeyPressedC2SPacket(int screen) {

    public static OpenExampleMenuKeyPressedC2SPacket decode(FriendlyByteBuf buf) {
        return new OpenExampleMenuKeyPressedC2SPacket(buf.readByte());
    }

    public static void encode(OpenExampleMenuKeyPressedC2SPacket packet, FriendlyByteBuf buf) {
        buf.writeByte(packet.screen);
    }

    public static void handle(OpenExampleMenuKeyPressedC2SPacket packet, CustomPayloadEvent.Context context) {
        Player player = context.getSender();
        int screen = packet.screen;
        if (player != null) {
            player.openMenu(new MenuProvider() {

                @Override
                public Component getDisplayName() {
                    if (screen > 0) {
                        return screen == 1 ? Component.literal("Example Menu") : Component.literal("Large Example Menu");
                    }
                    return Component.empty();
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    if (screen > 0) {
                        return (screen == 1 ? ModExamples.EXAMPLE_MENU : ModExamples.LARGE_EXAMPLE_MENU).get().create(id, inventory);
                    }
                    return null;
                }
            });
        }
    }
}

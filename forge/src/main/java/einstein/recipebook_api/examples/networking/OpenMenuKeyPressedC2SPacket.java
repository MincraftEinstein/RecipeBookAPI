package einstein.recipebook_api.examples.networking;

import einstein.recipebook_api.examples.menus.TestMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.jetbrains.annotations.Nullable;

public record OpenMenuKeyPressedC2SPacket() {

    public static OpenMenuKeyPressedC2SPacket decode(FriendlyByteBuf buf) {
        return new OpenMenuKeyPressedC2SPacket();
    }

    public static void encode(OpenMenuKeyPressedC2SPacket packet, FriendlyByteBuf buf) {
    }

    public static void handle(OpenMenuKeyPressedC2SPacket packet, CustomPayloadEvent.Context context) {
        Player player = context.getSender();
        if (player != null) {
            player.openMenu(new MenuProvider() {

                @Override
                public Component getDisplayName() {
                    return Component.literal("Test Menu");
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new TestMenu(id, inventory);
                }
            });
        }
    }
}

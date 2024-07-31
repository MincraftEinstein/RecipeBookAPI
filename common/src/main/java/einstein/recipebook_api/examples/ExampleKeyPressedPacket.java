package einstein.recipebook_api.examples;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import static einstein.recipebook_api.RecipeBookAPI.loc;

public record ExampleKeyPressedPacket(int screen) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ExampleKeyPressedPacket> TYPE = new CustomPacketPayload.Type<>(loc("open_example_menu_key_pressed"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ExampleKeyPressedPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> buf.writeInt(packet.screen), buf -> new ExampleKeyPressedPacket(buf.readInt()));

    public static void openExampleMenu(Player player, int screen) {
        if (player != null) {
            player.openMenu(new MenuProvider() {

                @Override
                public Component getDisplayName() {
                    if (screen > 0) {
                        return screen == 1 ? Component.literal("Example Menu") : Component.literal("Large Example Menu");
                    }
                    return Component.empty();
                }

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

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
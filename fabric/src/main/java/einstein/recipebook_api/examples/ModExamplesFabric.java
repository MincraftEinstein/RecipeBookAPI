package einstein.recipebook_api.examples;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.examples.screens.ExampleScreen;
import einstein.recipebook_api.examples.screens.LargeExampleScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class ModExamplesFabric {

    private static final ResourceLocation OPEN_EXAMPLE_MENU_KEY_PRESSED = RecipeBookAPI.loc("open_example_menu_key_pressed");

    public static void loadExamples() {
        ModExamples.init();
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            int screen = ModExamples.OPEN_EXAMPLE_MENU.consumeClick() ? 1 : ModExamples.OPEN_LARGE_EXAMPLE_MENU.consumeClick() ? 2 : 0;
            if (minecraft.level != null && screen > 0) {
                ClientPlayNetworking.send(OPEN_EXAMPLE_MENU_KEY_PRESSED, PacketByteBufs.create().writeByte(screen));
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(OPEN_EXAMPLE_MENU_KEY_PRESSED, (server, player, handler, buf, responseSender) -> {
            int screen = buf.readByte();
            server.execute(() -> {
                player.openMenu(new ExtendedScreenHandlerFactory() {

                    @Override
                    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                    }

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
                            return ((ExtendedScreenHandlerType<?>) (screen == 1 ? ModExamples.EXAMPLE_MENU : ModExamples.LARGE_EXAMPLE_MENU).get()).create(id, inventory, PacketByteBufs.create());
                        }
                        return null;
                    }
                });
            });
        });
    }

    public static void loadExamplesClient() {
        MenuScreens.register(ModExamples.EXAMPLE_MENU.get(), ExampleScreen::new);
        MenuScreens.register(ModExamples.LARGE_EXAMPLE_MENU.get(), LargeExampleScreen::new);
    }
}

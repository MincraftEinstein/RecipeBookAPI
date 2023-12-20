package einstein.recipebook_api.examples;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.examples.menus.ExampleMenu;
import einstein.recipebook_api.examples.screens.ExampleScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
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
            if (minecraft.level != null && ModExamples.OPEN_EXAMPLE_MENU.consumeClick()) {
                ClientPlayNetworking.send(OPEN_EXAMPLE_MENU_KEY_PRESSED, PacketByteBufs.create());
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(OPEN_EXAMPLE_MENU_KEY_PRESSED, (server, player, handler, buf, responseSender) -> {
            player.openMenu(new ExtendedScreenHandlerFactory() {

                @Override
                public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                }

                @Override
                public Component getDisplayName() {
                    return Component.literal("Example Menu");
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new ExampleMenu(id, inventory);
                }
            });
        });
    }

    public static void loadExamplesClient() {
        MenuScreens.register(ModExamples.EXAMPLE_MENU.get(), ExampleScreen::new);
    }
}

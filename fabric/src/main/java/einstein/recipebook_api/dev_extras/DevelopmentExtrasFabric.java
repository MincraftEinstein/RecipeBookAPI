package einstein.recipebook_api.dev_extras;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.dev_extras.menus.TestMenu;
import einstein.recipebook_api.dev_extras.screens.TestScreen;
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

public class DevelopmentExtrasFabric {

    private static final ResourceLocation OPEN_MENU_KEY_PRESSED = RecipeBookAPI.loc("open_menu_key_pressed");

    public static void loadDevelopmentExtras() {
        DevelopmentExtras.init();
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.level != null && DevelopmentExtras.OPEN_TEST_MENU.consumeClick()) {
                ClientPlayNetworking.send(OPEN_MENU_KEY_PRESSED, PacketByteBufs.create());
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(OPEN_MENU_KEY_PRESSED, (server, player, handler, buf, responseSender) -> {
            player.openMenu(new ExtendedScreenHandlerFactory() {

                @Override
                public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                }

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
        });
    }

    public static void loadClientDevelopmentExtras() {
        MenuScreens.register(DevelopmentExtras.TEST_MENU.get(), TestScreen::new);
    }
}

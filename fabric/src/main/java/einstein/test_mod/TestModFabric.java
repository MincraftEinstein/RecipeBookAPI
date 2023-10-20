package einstein.test_mod;

import einstein.test_mod.menus.TestMenu;
import einstein.test_mod.screens.TestScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
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

import static einstein.test_mod.TestMod.loc;

public class TestModFabric implements ModInitializer, ClientModInitializer {

    private final ResourceLocation OPEN_MENU_KEY_PRESSED = loc("open_menu_key_pressed");

    @Override
    public void onInitialize() {
        TestMod.init();
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.level != null && TestMod.OPEN_TEST_MENU.consumeClick()) {
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

    @Override
    public void onInitializeClient() {
        MenuScreens.register(TestMod.TEST_MENU.get(), TestScreen::new);
    }
}

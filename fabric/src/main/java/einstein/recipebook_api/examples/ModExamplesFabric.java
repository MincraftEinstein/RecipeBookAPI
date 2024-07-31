package einstein.recipebook_api.examples;

import einstein.recipebook_api.examples.screens.ExampleScreen;
import einstein.recipebook_api.examples.screens.LargeExampleScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ModExamplesFabric {

    public static void loadExamples() {
        ModExamples.init();
        PayloadTypeRegistry.playC2S().register(ExampleKeyPressedPacket.TYPE, ExampleKeyPressedPacket.STREAM_CODEC);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            int screen = ModExamples.OPEN_EXAMPLE_MENU.consumeClick() ? 1 : ModExamples.OPEN_LARGE_EXAMPLE_MENU.consumeClick() ? 2 : 0;
            if (minecraft.level != null && screen > 0) {
                ClientPlayNetworking.send(new ExampleKeyPressedPacket(screen));
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(ExampleKeyPressedPacket.TYPE, (packet, context) -> {
            int screen = packet.screen();
            ServerPlayer player = context.player();
            context.server().execute(() -> ExampleKeyPressedPacket.openExampleMenu(player, screen));
        });
    }

    public static void loadExamplesClient() {
        MenuScreens.register(ModExamples.EXAMPLE_MENU.get(), ExampleScreen::new);
        MenuScreens.register(ModExamples.LARGE_EXAMPLE_MENU.get(), LargeExampleScreen::new);
    }
}

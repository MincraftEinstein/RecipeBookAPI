package einstein.recipebook_api.examples;

import einstein.recipebook_api.examples.screens.ExampleScreen;
import einstein.recipebook_api.examples.screens.LargeExampleScreen;
import einstein.recipebook_api.platform.NeoForgeRegistryHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModExamplesForge {

    public static void loadExamples(IEventBus modEventBus) {
        ModExamples.init();
        NeoForgeRegistryHelper.REGISTRIES.forEach((registry, deferredRegister) ->
                deferredRegister.register(modEventBus));
        modEventBus.addListener((RegisterKeyMappingsEvent event) ->
                NeoForgeRegistryHelper.KEY_MAPPINGS.forEach(supplier ->
                        event.register(supplier.get())
                )
        );
        modEventBus.addListener((RegisterMenuScreensEvent event) -> {
            event.register(ModExamples.EXAMPLE_MENU.get(), ExampleScreen::new);
            event.register(ModExamples.LARGE_EXAMPLE_MENU.get(), LargeExampleScreen::new);
        });
        modEventBus.addListener((RegisterPayloadHandlersEvent event) -> {
            PayloadRegistrar registrar = event.registrar("1");
            registrar.playToServer(ExampleKeyPressedPacket.TYPE, ExampleKeyPressedPacket.STREAM_CODEC, (packet, context) ->
                    ExampleKeyPressedPacket.openExampleMenu(context.player(), packet.screen()));
        });
        NeoForge.EVENT_BUS.addListener((InputEvent.Key event) -> {
            Minecraft minecraft = Minecraft.getInstance();
            int screen = event.getKey() == ModExamples.OPEN_EXAMPLE_MENU.getKey().getValue() ? 1 : event.getKey() == ModExamples.OPEN_LARGE_EXAMPLE_MENU.getKey().getValue() ? 2 : 0;
            if (minecraft.level != null && screen > 0) {
                PacketDistributor.sendToServer(new ExampleKeyPressedPacket(screen));
            }
        });
    }
}

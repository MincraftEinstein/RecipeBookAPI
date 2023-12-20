package einstein.recipebook_api.examples;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.RecipeBookAPIForge;
import einstein.recipebook_api.examples.networking.OpenExampleMenuKeyPressedC2SPacket;
import einstein.recipebook_api.examples.screens.ExampleScreen;
import einstein.recipebook_api.platform.ForgeRegistryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.*;

public class ModExamplesForge {

    public static final SimpleChannel CHANNEL = ChannelBuilder.named(RecipeBookAPI.loc("examples_main"))
            .networkProtocolVersion(1)
            .clientAcceptedVersions(Channel.VersionTest.exact(1))
            .serverAcceptedVersions(Channel.VersionTest.exact(1))
            .simpleChannel();

    public static void loadExamples(IEventBus modEventBus) {
        ModExamples.init();
        RecipeBookAPIForge.registerRecipeBooks(RecipeBookAPI.MOD_ID, modEventBus);
        ForgeRegistryHelper.REGISTRIES.forEach((registry, deferredRegister) ->
                deferredRegister.register(modEventBus));
        modEventBus.addListener((RegisterKeyMappingsEvent event) ->
                ForgeRegistryHelper.KEY_MAPPINGS.forEach(supplier ->
                        event.register(supplier.get())
                )
        );
        modEventBus.addListener((FMLCommonSetupEvent event) -> {
            CHANNEL.messageBuilder(OpenExampleMenuKeyPressedC2SPacket.class, NetworkDirection.PLAY_TO_SERVER)
                    .decoder(OpenExampleMenuKeyPressedC2SPacket::decode)
                    .encoder(OpenExampleMenuKeyPressedC2SPacket::encode)
                    .consumerMainThread(OpenExampleMenuKeyPressedC2SPacket::handle)
                    .add();
        });
        modEventBus.addListener((FMLClientSetupEvent event) -> {
            MenuScreens.register(ModExamples.EXAMPLE_MENU.get(), ExampleScreen::new);
        });
        MinecraftForge.EVENT_BUS.addListener((InputEvent.Key event) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null && event.getKey() == ModExamples.OPEN_EXAMPLE_MENU.getKey().getValue()) {
                CHANNEL.send(new OpenExampleMenuKeyPressedC2SPacket(), PacketDistributor.SERVER.noArg());
            }
        });
    }
}

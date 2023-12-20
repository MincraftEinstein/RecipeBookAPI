package einstein.recipebook_api.examples;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.RecipeBookAPIForge;
import einstein.recipebook_api.examples.networking.OpenMenuKeyPressedC2SPacket;
import einstein.recipebook_api.examples.screens.TestScreen;
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

    public static final SimpleChannel CHANNEL = ChannelBuilder.named(RecipeBookAPI.loc("main"))
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
            CHANNEL.messageBuilder(OpenMenuKeyPressedC2SPacket.class, NetworkDirection.PLAY_TO_SERVER)
                    .decoder(OpenMenuKeyPressedC2SPacket::decode)
                    .encoder(OpenMenuKeyPressedC2SPacket::encode)
                    .consumerMainThread(OpenMenuKeyPressedC2SPacket::handle)
                    .add();
        });
        modEventBus.addListener((FMLClientSetupEvent event) -> {
            MenuScreens.register(ModExamples.TEST_MENU.get(), TestScreen::new);
        });
        MinecraftForge.EVENT_BUS.addListener((InputEvent.Key event) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null && event.getKey() == ModExamples.OPEN_TEST_MENU.getKey().getValue()) {
                CHANNEL.send(new OpenMenuKeyPressedC2SPacket(), PacketDistributor.SERVER.noArg());
            }
        });
    }
}

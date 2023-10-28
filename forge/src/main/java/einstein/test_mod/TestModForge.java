package einstein.test_mod;

import einstein.recipebook_api.RecipeBookAPIForge;
import einstein.test_mod.networking.OpenMenuKeyPressedC2SPacket;
import einstein.test_mod.screens.TestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.*;

@Mod(TestMod.MOD_ID)
public class TestModForge {

    public static final SimpleChannel INSTANCE = ChannelBuilder.named(TestMod.loc("main"))
            .networkProtocolVersion(1)
            .clientAcceptedVersions(Channel.VersionTest.exact(1))
            .serverAcceptedVersions(Channel.VersionTest.exact(1))
            .simpleChannel();

    public TestModForge() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TestMod.init();
        RecipeBookAPIForge.registerRecipeBooks(modEventBus);
        ForgeRegistryHelper.REGISTRIES.forEach((registry, deferredRegister) ->
                deferredRegister.register(modEventBus));
        modEventBus.addListener((RegisterKeyMappingsEvent event) ->
                ForgeRegistryHelper.KEY_MAPPINGS.forEach(supplier ->
                        event.register(supplier.get())
                )
        );
        modEventBus.addListener((FMLCommonSetupEvent event) -> {
            INSTANCE.messageBuilder(OpenMenuKeyPressedC2SPacket.class, NetworkDirection.PLAY_TO_SERVER).decoder(OpenMenuKeyPressedC2SPacket::decode).encoder(OpenMenuKeyPressedC2SPacket::encode).consumerMainThread(OpenMenuKeyPressedC2SPacket::handle).add();
        });
        modEventBus.addListener((FMLClientSetupEvent event) -> {
            MenuScreens.register(TestMod.TEST_MENU.get(), TestScreen::new);
        });
        MinecraftForge.EVENT_BUS.addListener((InputEvent.Key event) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null && event.getKey() == TestMod.OPEN_TEST_MENU.getKey().getValue()) {
                INSTANCE.send(new OpenMenuKeyPressedC2SPacket(), PacketDistributor.SERVER.noArg());
            }
        });
    }
}

package einstein.test_mod;

import einstein.recipebook_api.RecipeBookAPIForge;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestMod.MOD_ID)
public class TestModForge {

    public TestModForge() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TestMod.init();
        RecipeBookAPIForge.register(modEventBus);
        ForgeRegistryHelper.REGISTRIES.forEach((registry, deferredRegister) ->
                deferredRegister.register(modEventBus));
        modEventBus.addListener((RegisterKeyMappingsEvent event) ->
                ForgeRegistryHelper.KEY_MAPPINGS.forEach(supplier ->
                        event.register(supplier.get())
                )
        );
    }
}

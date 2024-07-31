package einstein.recipebook_api;

import einstein.recipebook_api.examples.ModExamplesForge;
import einstein.recipebook_api.platform.Services;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(RecipeBookAPI.MOD_ID)
public class RecipeBookAPINeoForge {

    public RecipeBookAPINeoForge(IEventBus modEventBus) {
        RecipeBookAPI.init();

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            ModExamplesForge.loadExamples(modEventBus);
        }
    }
}
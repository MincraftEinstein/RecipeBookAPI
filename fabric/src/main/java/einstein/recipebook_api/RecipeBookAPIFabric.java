package einstein.recipebook_api;

import einstein.recipebook_api.examples.ModExamplesFabric;
import einstein.recipebook_api.platform.Services;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class RecipeBookAPIFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        RecipeBookAPI.init();
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            ModExamplesFabric.loadExamples();
        }
    }

    @Override
    public void onInitializeClient() {
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            ModExamplesFabric.loadExamplesClient();
        }
    }
}

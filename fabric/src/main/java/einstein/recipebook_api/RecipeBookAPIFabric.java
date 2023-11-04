package einstein.recipebook_api;

import einstein.recipebook_api.dev_extras.DevelopmentExtrasFabric;
import einstein.recipebook_api.platform.Services;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class RecipeBookAPIFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        RecipeBookAPI.init();
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            DevelopmentExtrasFabric.loadDevelopmentExtras();
        }
    }

    @Override
    public void onInitializeClient() {
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            DevelopmentExtrasFabric.loadClientDevelopmentExtras();
        }
    }
}

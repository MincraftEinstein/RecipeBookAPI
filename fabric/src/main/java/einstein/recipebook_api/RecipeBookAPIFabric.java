package einstein.recipebook_api;

import net.fabricmc.api.ModInitializer;

public class RecipeBookAPIFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        RecipeBookAPI.init();
    }
}

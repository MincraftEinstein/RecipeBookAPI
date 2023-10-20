package einstein.recipebook_api;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeBookAPI {

    public static final String MOD_ID = "recipebook_api";
    public static final String MOD_NAME = "RecipeBook API";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String enumName(ResourceLocation id) {
        return MOD_ID + "$" + id.getNamespace() + "$" + id.getPath().toUpperCase();
    }

    public static Pair<String, String> getRecipeBookTags(ResourceLocation id) {
        String serialized = MOD_ID + "." + id;
        return Pair.of("is" + serialized + "GuiOpen", "is" + serialized + "FilteringCraftable");
    }
}

package einstein.recipebook_api.mixin;

import com.mojang.datafixers.util.Pair;
import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.api.RecipeBookRegistry;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeBookSettings.class)
public class RecipeBookSettingsMixin {

    @Accessor("TAG_FIELDS")
    private static Map<RecipeBookType, Pair<String, String>> getTagFields() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("TAG_FIELDS")
    private static void setTagFields(Map<RecipeBookType, Pair<String, String>> map) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void clInit(CallbackInfo ci) {
        Map<RecipeBookType, Pair<String, String>> tagFields = new HashMap<>(getTagFields());
        RecipeBookRegistry.RECIPE_BOOK_REGISTRY.forEach((modId, registry) -> {
            registry.getTypes().forEach((recipeType, holder) -> {
                RecipeBookType type = holder.getType();
                if (type != null) {
                    String name = holder.getType().name().toLowerCase(java.util.Locale.ROOT).replace("_", "");
                    tagFields.put(holder.getType(), Pair.of("is" + name + "GuiOpen", "is" + name + "FilteringCraftable"));
                }
                else {
                    RecipeBookAPI.LOGGER.error("Cannot add Recipe Book tags for null type: {}", modId + ":" + holder.getName());
                }
            });
        });
        setTagFields(tagFields);
    }
}

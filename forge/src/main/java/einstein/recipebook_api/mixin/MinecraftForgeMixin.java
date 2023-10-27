package einstein.recipebook_api.mixin;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.api.RecipeBookRegistry;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftForge.class)
public class MinecraftForgeMixin {

    @Inject(method = "initialize", at = @At("HEAD"), remap = false)
    private static void initialize(CallbackInfo ci) {
        RecipeBookRegistry.RECIPE_BOOK_REGISTRY.forEach((modId, registry) -> {
            registry.getTypes().forEach((recipeType, typeHolder) -> {
                typeHolder.setType(RecipeBookType.create(RecipeBookAPI.enumName(modId, typeHolder.getName())));

                typeHolder.getAllCategoryHolders().forEach(categoryHolder -> {
                    RecipeBookCategories category = RecipeBookCategories.create(RecipeBookAPI.enumName(modId, categoryHolder.getName()), categoryHolder.getIconStacks());
                    categoryHolder.setCategory(category);
                });
            });
        });
    }
}

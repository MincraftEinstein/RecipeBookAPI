package einstein.recipebook_api.mixin;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.api.CategorizedRecipe;
import einstein.recipebook_api.api.RecipeBookCategoryHolder;
import einstein.recipebook_api.api.RecipeBookRegistry;
import einstein.recipebook_api.api.RecipeBookTypeHolder;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "getCategory", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/Recipe;getType()Lnet/minecraft/world/item/crafting/RecipeType;"), cancellable = true)
    private static void getCategory(RecipeHolder<?> holder, CallbackInfoReturnable<RecipeBookCategories> cir) {
        Recipe<?> recipe = holder.value();
        RecipeType<?> recipeType = recipe.getType();

        for (String modId : RecipeBookRegistry.RECIPE_BOOK_REGISTRY.keySet()) {
            RecipeBookRegistry registry = RecipeBookRegistry.RECIPE_BOOK_REGISTRY.get(modId);
            final boolean[] hasEnded = {false};

            registry.getTypes().forEach((typeSupplier, typeHolder) -> {
                if (hasEnded[0]) {
                    return;
                }

                if (recipeType.equals(typeSupplier.get())) {
                    hasEnded[0] = true;
                    cir.setReturnValue(RecipeBookAPI.getCategory(recipe, typeHolder));
                }
            });

            if (hasEnded[0]) {
                return;
            }
        }
    }
}

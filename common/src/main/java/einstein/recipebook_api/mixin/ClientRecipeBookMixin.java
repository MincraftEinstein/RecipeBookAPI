package einstein.recipebook_api.mixin;

import einstein.recipebook_api.api.CategorizedRecipe;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "getCategory", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/Recipe;getType()Lnet/minecraft/world/item/crafting/RecipeType;"), cancellable = true)
    private static void getCategory(RecipeHolder<?> holder, CallbackInfoReturnable<RecipeBookCategories> cir) {
        Recipe<?> recipe = holder.value();
        if (recipe instanceof CategorizedRecipe categorizedRecipe) {
            cir.setReturnValue(categorizedRecipe.getRecipeBookCategory().getCategory());
        }
    }
}

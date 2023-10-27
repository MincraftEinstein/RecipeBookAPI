package einstein.recipebook_api.mixin;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.api.RecipeBookRegistry;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(RecipeBookCategories.class)
public class RecipeBookCategoriesMixin {

    @Shadow
    @Final
    @Mutable
    private static RecipeBookCategories[] $VALUES;

    @Accessor("AGGREGATE_CATEGORIES")
    private static Map<RecipeBookCategories, List<RecipeBookCategories>> getAggregateCategories() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("AGGREGATE_CATEGORIES")
    private static void setAggregateCategories(Map<RecipeBookCategories, List<RecipeBookCategories>> map) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void clInit(CallbackInfo ci) {
        Map<RecipeBookCategories, List<RecipeBookCategories>> aggregateCategories = new HashMap<>(getAggregateCategories());

        RecipeBookRegistry.RECIPE_BOOK_REGISTRY.forEach((modId, registry) -> {
            registry.getTypes().forEach((recipeType, type) -> {
                type.getAllCategoryHolders().forEach(categoryHolder -> {
                    RecipeBookCategories category = recipeBookAPI$register(modId, categoryHolder.getName(), categoryHolder.getIconStacks());
                    categoryHolder.setCategory(category);
                });
                aggregateCategories.put(type.getSearchCategory().getCategory(), type.getCategories());
            });
        });

        setAggregateCategories(aggregateCategories);
    }

    @Invoker("<init>")
    private static RecipeBookCategories invokeInit(String name, int id, ItemStack... stacks) {
        throw new AssertionError();
    }

    @Unique
    private static RecipeBookCategories recipeBookAPI$register(String modId, String name, ItemStack... stacks) {
        ArrayList<RecipeBookCategories> values = new ArrayList<>(Arrays.asList($VALUES));
        RecipeBookCategories category = invokeInit(RecipeBookAPI.enumName(modId, name), values.get(values.size() - 1).ordinal() + 1, stacks);
        values.add(category);
        $VALUES = values.toArray(new RecipeBookCategories[]{});
        return category;
    }

    @Inject(method = "getCategories", at = @At("HEAD"), cancellable = true)
    private static void getCategories(RecipeBookType type, CallbackInfoReturnable<List<RecipeBookCategories>> cir) {
        RecipeBookRegistry.RECIPE_BOOK_REGISTRY.forEach((modId, registry) -> {
            registry.getTypes().forEach((recipeType, typeHolder) -> {
                if (type.equals(typeHolder.getType())) {
                    cir.setReturnValue(typeHolder.getAllCategories());
                }
            });
        });
    }
}
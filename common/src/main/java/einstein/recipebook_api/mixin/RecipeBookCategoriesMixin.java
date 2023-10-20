package einstein.recipebook_api.mixin;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.api.RecipeBookCategoryGroup;
import einstein.recipebook_api.api.RecipeBookCategoryHolder;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import einstein.recipebook_api.api.RecipeBookTypeHolder;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
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

        for (RecipeBookCategoryHolder holder : RecipeBookRegistryImpl.CATEGORY_REGISTRY) {
            RecipeBookCategories category = justMoreCakes$register(holder.getId(), holder.getIconStacks());
            holder.setCategory(category);
        }

        for (RecipeBookCategoryGroup group : RecipeBookRegistryImpl.CATEGORY_GROUP_REGISTRY) {
            aggregateCategories.put(group.mainCategory().getCategory(), group.categories().stream().map(RecipeBookCategoryHolder::getCategory).toList());
        }

        setAggregateCategories(aggregateCategories);
    }

    @Invoker("<init>")
    private static RecipeBookCategories invokeInit(String name, int id, ItemStack... stacks) {
        throw new AssertionError();
    }

    @Unique
    private static RecipeBookCategories justMoreCakes$register(ResourceLocation id, ItemStack... stacks) {
        ArrayList<RecipeBookCategories> values = new ArrayList<>(Arrays.asList($VALUES));
        RecipeBookCategories category = invokeInit(RecipeBookAPI.enumName(id), values.get(values.size() - 1).ordinal() + 1, stacks);
        values.add(category);
        $VALUES = values.toArray(new RecipeBookCategories[]{});
        return category;
    }

    @Inject(method = "getCategories", at = @At("HEAD"), cancellable = true)
    private static void getCategories(RecipeBookType type, CallbackInfoReturnable<List<RecipeBookCategories>> cir) {
        for (RecipeBookTypeHolder holder : RecipeBookRegistryImpl.TYPE_REGISTRY) {
            if (type.equals(holder.getType())) {
                cir.setReturnValue(holder.getGroup().getAllCategories());
            }
        }
    }
}
package einstein.recipebook_api.api;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RecipeBookCategoryHolder {

    private RecipeBookCategories category;
    private RecipeBookTypeHolder type;
    private final ResourceLocation id;
    private final ItemStack[] iconStacks;

    public RecipeBookCategoryHolder(ResourceLocation id, ItemStack... iconStacks) {
        this.id = id;
        this.iconStacks = iconStacks;
    }

    public ResourceLocation getId() {
        return id;
    }

    public ItemStack[] getIconStacks() {
        return iconStacks;
    }

    public RecipeBookTypeHolder getType() {
        return type;
    }

    public void setType(RecipeBookTypeHolder type) {
        this.type = type;
    }

    public RecipeBookCategories getCategory() {
        return category;
    }

    public void setCategory(RecipeBookCategories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

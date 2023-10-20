package einstein.recipebook_api.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;

public class RecipeBookTypeHolder {

    private RecipeBookType type;
    private final ResourceLocation id;
    private final RecipeBookCategoryGroup group;

    public RecipeBookTypeHolder(ResourceLocation id, RecipeBookCategoryGroup group) {
        this.id = id;
        this.group = group;
    }

    public ResourceLocation getId() {
        return id;
    }

    public RecipeBookCategoryGroup getGroup() {
        return group;
    }

    public RecipeBookType getType() {
        return type;
    }

    public void setType(RecipeBookType type) {
        this.type = type;
    }
}

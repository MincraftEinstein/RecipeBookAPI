package einstein.recipebook_api.api;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

public interface RecipeBookCategoryEnum extends StringRepresentable {

    String getName();

    ItemStack[] getIconStacks();

    @Override
    default String getSerializedName() {
        return getName();
    }
}

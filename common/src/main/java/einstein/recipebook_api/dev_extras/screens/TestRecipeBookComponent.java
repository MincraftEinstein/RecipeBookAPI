package einstein.recipebook_api.dev_extras.screens;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class TestRecipeBookComponent extends RecipeBookComponent {

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < menu.getSize()) {
            ghostRecipe.clear();
        }
    }

    @Override
    public void setupGhostRecipe(RecipeHolder<?> holder, List<Slot> slots) {
        Recipe<?> recipe = holder.value();
        ItemStack resultStack = recipe.getResultItem(minecraft.level.registryAccess());
        Slot resultSlot = slots.get(2);
        ghostRecipe.setRecipe(holder);
        ghostRecipe.addIngredient(Ingredient.of(resultStack), resultSlot.x, resultSlot.y);

        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        Iterator<Ingredient> ingredientIterator = ingredients.iterator();
        for (int i = 0; i < ingredients.size(); ++i) {
            if (!ingredientIterator.hasNext()) {
                return;
            }

            Ingredient ingredient = ingredientIterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot = slots.get(i);
                ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
            }
        }
    }
}

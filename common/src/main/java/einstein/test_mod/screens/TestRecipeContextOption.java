package einstein.test_mod.screens;

import einstein.recipebook_api.api.screen.RecipeContextMenuOption;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

import static einstein.test_mod.TestMod.EMPTY;

public class TestRecipeContextOption extends RecipeContextMenuOption {

    private final List<Pos> positions = new ArrayList<>();

    @Override
    public WidgetSprites getSprites() {
        return new WidgetSprites(EMPTY, EMPTY);
    }

    @Override
    public void calculateIngredientsPositions(RecipeHolder<?> recipeHolder) {
        Ingredient ingredient = recipeHolder.value().getIngredients().get(0);
        Ingredient ingredient1 = recipeHolder.value().getIngredients().get(1);

        positions.add(new Pos(10, 5, ingredient));
        positions.add(new Pos(10, 15, ingredient1));
    }

    @Override
    public List<Pos> getIngredientPositions() {
        return positions;
    }
}

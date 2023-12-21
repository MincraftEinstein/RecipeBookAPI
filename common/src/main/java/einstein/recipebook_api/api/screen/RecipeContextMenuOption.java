package einstein.recipebook_api.api.screen;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public abstract class RecipeContextMenuOption {

    public static final WidgetSprites CRAFTING_ITEM_SPRITES = new WidgetSprites(
            OverlayRecipeComponent.CRAFTING_OVERLAY_SPRITE,
            OverlayRecipeComponent.CRAFTING_OVERLAY_DISABLED_SPRITE,
            OverlayRecipeComponent.CRAFTING_OVERLAY_HIGHLIGHTED_SPRITE,
            OverlayRecipeComponent.CRAFTING_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE
    );

    public static final WidgetSprites SINGLE_ITEM_SPRITES = new WidgetSprites(
            OverlayRecipeComponent.FURNACE_OVERLAY_SPRITE,
            OverlayRecipeComponent.FURNACE_OVERLAY_DISABLED_SPRITE,
            OverlayRecipeComponent.FURNACE_OVERLAY_HIGHLIGHTED_SPRITE,
            OverlayRecipeComponent.FURNACE_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE
    );

    public abstract WidgetSprites getSprites();

    public abstract List<Pos> calculateIngredientsPositions(RecipeHolder<?> recipeHolder);

    public record Pos(int x, int y, Ingredient ingredient) {

    }
}

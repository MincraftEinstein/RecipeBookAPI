package einstein.recipebook_api.mixin;

import einstein.recipebook_api.api.screen.RecipeContextMenuOption;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Supplier;

@Mixin(OverlayRecipeComponent.OverlayRecipeButton.class)
public abstract class OverlayRecipeButtonMixin extends AbstractWidget {

    @Shadow
    @Final
    private boolean isCraftable;

    @Shadow
    @Final
    protected List<OverlayRecipeComponent.OverlayRecipeButton.Pos> ingredientPos;

    @Unique
    private RecipeContextMenuOption recipeBookAPI$menuOption;

    @Unique
    @SuppressWarnings("all")
    private OverlayRecipeComponent.OverlayRecipeButton recipeBookAPI$me = (OverlayRecipeComponent.OverlayRecipeButton) (Object) (this);

    public OverlayRecipeButtonMixin(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/recipebook/OverlayRecipeComponent$OverlayRecipeButton;calculateIngredientsPositions(Lnet/minecraft/world/item/crafting/RecipeHolder;)V"))
    private void setup(OverlayRecipeComponent.OverlayRecipeButton recipeButton, RecipeHolder<?> recipeHolder) {
        recipeBookAPI$setView(recipeHolder);
        if (recipeBookAPI$menuOption != null) {
            recipeBookAPI$menuOption.calculateIngredientsPositions(recipeHolder);
            for (RecipeContextMenuOption.Pos pos : recipeBookAPI$menuOption.getIngredientPositions()) {
                ingredientPos.add(recipeBookAPI$me.new Pos(pos.x(), pos.y(), pos.ingredient().getItems()));
            }
            return;
        }
        recipeButton.calculateIngredientsPositions(recipeHolder);
    }

    @Unique
    private void recipeBookAPI$setView(RecipeHolder<?> recipeHolder) {
        for (String modId : RecipeBookRegistryImpl.RECIPE_BOOK_REGISTRY.keySet()) {
            RecipeBookRegistryImpl registry = RecipeBookRegistryImpl.RECIPE_BOOK_REGISTRY.get(modId);
            for (Supplier<? extends RecipeType<?>> recipeType : registry.getRecipeContextMenuOptions().keySet()) {
                if (recipeType.get().equals(recipeHolder.value().getType())) {
                    recipeBookAPI$menuOption = registry.getRecipeContextMenuOptions().get(recipeType).get();
                    return;
                }
            }
        }
    }

    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private void renderSprite(GuiGraphics guiGraphics, ResourceLocation sprite, int x, int y, int height, int width) {
        if (recipeBookAPI$menuOption != null) {
            if (isCraftable) {
                sprite = isHoveredOrFocused() ? recipeBookAPI$menuOption.getSprites().enabledFocused() : recipeBookAPI$menuOption.getSprites().enabled();
            }
            else {
                sprite = isHoveredOrFocused() ? recipeBookAPI$menuOption.getSprites().disabledFocused() : recipeBookAPI$menuOption.getSprites().disabled();
            }
        }
        guiGraphics.blitSprite(sprite, x, y, height, width);
    }
}

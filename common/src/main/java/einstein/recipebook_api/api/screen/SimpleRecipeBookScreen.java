package einstein.recipebook_api.api.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import org.joml.Vector2i;

public abstract class SimpleRecipeBookScreen<T extends RecipeBookMenu<?, ?>, V extends RecipeBookComponent> extends AbstractContainerScreen<T> implements RecipeUpdateListener {

    protected boolean widthTooNarrow;
    private final V recipeBookComponent;

    public SimpleRecipeBookScreen(T menu, Inventory inventory, Component title, V recipeBookComponent) {
        super(menu, inventory, title);
        this.recipeBookComponent = recipeBookComponent;
    }

    @Override
    protected void init() {
        super.init();
        widthTooNarrow = width < 379;
        recipeBookComponent.init(width, height, minecraft, widthTooNarrow, menu);
        leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
        addRenderableWidget(new ImageButton(recipeButtonPos().x(), recipeButtonPos().y(),
                recipeButtonSize().x(), recipeButtonSize().y(), recipeButtonSprites(), button -> {
            recipeBookComponent.toggleVisibility();
            leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
            button.setPosition(recipeButtonPos().x(), recipeButtonPos().y());
        }));
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        recipeBookComponent.tick();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (recipeBookComponent.isVisible() && widthTooNarrow) {
            renderBackground(guiGraphics, mouseX, mouseX, partialTick);
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        else {
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);
            recipeBookComponent.renderGhostRecipe(guiGraphics, leftPos, topPos, true, partialTick);
        }

        renderTooltip(guiGraphics, mouseX, mouseY);
        recipeBookComponent.renderTooltip(guiGraphics, leftPos, topPos, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (recipeBookComponent.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }
        return widthTooNarrow && recipeBookComponent.isVisible() || super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void slotClicked(Slot slot, int slotIndex, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotIndex, mouseButton, type);
        recipeBookComponent.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !recipeBookComponent.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        return recipeBookComponent.hasClickedOutside(mouseX, mouseY, leftPos, topPos, imageWidth, imageHeight, mouseButton)
                && super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return recipeBookComponent.charTyped(codePoint, modifiers) || super.charTyped(codePoint, modifiers);
    }

    @Override
    public void recipesUpdated() {
        recipeBookComponent.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return recipeBookComponent;
    }

    protected Vector2i recipeButtonPos() {
        return new Vector2i(leftPos + 20, height / 2 - 49);
    }

    protected Vector2i recipeButtonSize() {
        return new Vector2i(20, 18);
    }

    protected WidgetSprites recipeButtonSprites() {
        return RecipeBookComponent.RECIPE_BUTTON_SPRITES;
    }
}

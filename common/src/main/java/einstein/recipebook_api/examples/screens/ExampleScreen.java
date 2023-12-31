package einstein.recipebook_api.examples.screens;

import einstein.recipebook_api.api.screen.SimpleRecipeBookScreen;
import einstein.recipebook_api.examples.ModExamples;
import einstein.recipebook_api.examples.menus.ExampleMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ExampleScreen extends SimpleRecipeBookScreen<ExampleMenu, ExampleRecipeBookComponent> {

    public ExampleScreen(ExampleMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, new ExampleRecipeBookComponent());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(ModExamples.EMPTY, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}

package einstein.recipebook_api.examples.screens;

import einstein.recipebook_api.api.screen.SimpleRecipeBookScreen;
import einstein.recipebook_api.examples.ModExamples;
import einstein.recipebook_api.examples.menus.LargeExampleMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LargeExampleScreen extends SimpleRecipeBookScreen<LargeExampleMenu, ExampleRecipeBookComponent> {

    public LargeExampleScreen(LargeExampleMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, new ExampleRecipeBookComponent());
        imageHeight = 200;
        imageWidth = 200;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(ModExamples.EMPTY, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}

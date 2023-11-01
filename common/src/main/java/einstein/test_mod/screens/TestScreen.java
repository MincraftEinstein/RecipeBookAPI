package einstein.test_mod.screens;

import einstein.recipebook_api.api.SimpleRecipeBookScreen;
import einstein.test_mod.TestMod;
import einstein.test_mod.menus.TestMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TestScreen extends SimpleRecipeBookScreen<TestMenu, TestRecipeBookComponent> {

    public TestScreen(TestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, new TestRecipeBookComponent());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TestMod.EMPTY, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}

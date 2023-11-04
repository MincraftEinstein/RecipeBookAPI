package einstein.recipebook_api.dev_extras.screens;

import einstein.recipebook_api.api.screen.SimpleRecipeBookScreen;
import einstein.recipebook_api.dev_extras.DevelopmentExtras;
import einstein.recipebook_api.dev_extras.menus.TestMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TestScreen extends SimpleRecipeBookScreen<TestMenu, TestRecipeBookComponent> {

    public TestScreen(TestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, new TestRecipeBookComponent());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(DevelopmentExtras.EMPTY, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}

package einstein.recipebook_api.examples.menus;

import einstein.recipebook_api.examples.ModExamples;
import einstein.recipebook_api.examples.recipes.ExampleRecipe;
import einstein.recipebook_api.examples.recipes.ExampleRecipeInput;
import net.minecraft.client.player.Input;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ExampleMenu extends RecipeBookMenu<ExampleRecipeInput, ExampleRecipe> {

    private final Player player;
    private final Level level;
    private final Container container = new SimpleContainer(2) {

        @Override
        public void setChanged() {
            super.setChanged();
            slotsChanged(container);
        }
    };
    private final ResultContainer resultContainer = new ResultContainer();

    public ExampleMenu(int id, Inventory inventory) {
        super(ModExamples.EXAMPLE_MENU.get(), id);
        player = inventory.player;
        level = player.level();

        addSlot(new Slot(container, 0, 50, 30));
        addSlot(new Slot(container, 1, 50, 48));
        addSlot(new Slot(resultContainer, 0, 130, 35) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                if (!player.level().isClientSide) {
                    container.setChanged();
                }
                super.onTake(player, stack);
            }
        });

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            addSlot(new Slot(inventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void slotsChanged(Container container) {
        if (!level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ItemStack resultStack = ItemStack.EMPTY;
            Optional<RecipeHolder<ExampleRecipe>> optional = level.getServer().getRecipeManager().getRecipeFor(ModExamples.EXAMPLE_RECIPE_TYPE.get(), new ExampleRecipeInput(container), level);
            if (optional.isPresent()) {
                RecipeHolder<ExampleRecipe> holder = optional.get();
                ExampleRecipe recipe = holder.value();
                if (resultContainer.setRecipeUsed(level, serverPlayer, holder)) {
                    ItemStack resultStack2 = recipe.assemble(new ExampleRecipeInput(container), level.registryAccess());
                    if (resultStack2.isItemEnabled(level.enabledFeatures())) {
                        resultStack = resultStack2;
                    }
                }
            }

            resultContainer.setItem(2, resultStack);
            setRemoteSlot(2, resultStack);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(containerId, incrementStateId(), 2, resultStack));
        }
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
        if (container instanceof StackedContentsCompatible contentsCompatible) {
            contentsCompatible.fillStackedContents(stackedContents);
        }
    }

    @Override
    public void clearCraftingContent() {
        for (Slot slot : slots) {
            slot.set(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean recipeMatches(RecipeHolder<ExampleRecipe> holder) {
        return holder.value().matches(new ExampleRecipeInput(container), level);
    }

    @Override
    public int getResultSlotIndex() {
        return 2;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 2;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return ModExamples.EXAMPLE_TYPE.getType();
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}

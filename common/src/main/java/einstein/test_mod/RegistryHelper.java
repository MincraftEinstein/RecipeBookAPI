package einstein.test_mod;

import net.minecraft.client.KeyMapping;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public interface RegistryHelper {

    <T> Supplier<T> register(String name, Registry<?> registry, Supplier<T> supplier);

    <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier);

    KeyMapping registerKeyMapping(Supplier<KeyMapping> keyMapping);

    @FunctionalInterface
    interface MenuTypeSupplier<T> {

        T create(int id, Inventory inventory, FriendlyByteBuf buf);
    }
}

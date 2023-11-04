package einstein.recipebook_api.platform;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.platform.services.RegistryHelper;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class FabricRegistryHelper implements RegistryHelper {

    @Override
    public <T> Supplier<T> register(String name, Registry<?> registry, Supplier<T> supplier) {
        T t = Registry.register((Registry<? super T>) registry, RecipeBookAPI.loc(name), supplier.get());
        return () -> t;
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier) {
        return new ExtendedScreenHandlerType<>(supplier::create);
    }

    @Override
    public KeyMapping registerKeyMapping(Supplier<KeyMapping> keyMapping) {
        KeyMapping mapping = keyMapping.get();
        return KeyBindingHelper.registerKeyBinding(mapping);
    }
}

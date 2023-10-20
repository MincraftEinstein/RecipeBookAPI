package einstein.test_mod;

import net.minecraft.client.KeyMapping;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ForgeRegistryHelper implements RegistryHelper {

    public static final Map<Registry<?>, DeferredRegister<?>> REGISTRIES = new HashMap<>();

    @Override
    public <T> Supplier<T> register(String name, Registry<?> registry, Supplier<T> supplier) {
        return null;
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier) {
        return null;
    }

    @Override
    public KeyMapping registerKeyMapping(Supplier<KeyMapping> keyMapping) {
        return null;
    }
}

package einstein.recipebook_api.api.screen;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class FilterButtonSpriteHelper {

    public static final WidgetSprites FURNACE_FILTER_SPRITES = create(ResourceLocation.withDefaultNamespace("furnace_filter"));
    public static final WidgetSprites CRAFTING_FILTER_SPRITES = create(ResourceLocation.withDefaultNamespace("filter"));

    public static WidgetSprites create(ResourceLocation texture) {
        ResourceLocation location = texture.withPrefix("recipe_book/");
        return new WidgetSprites(
                location.withSuffix("_enabled"),
                location.withSuffix("_disabled"),
                location.withSuffix("_enabled_highlighted"),
                location.withSuffix("_disabled_highlighted")
        );
    }
}

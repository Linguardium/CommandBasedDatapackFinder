package mod.linguardium.commandpackfinder.mixins;

import net.minecraft.registry.tag.TagGroupLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TagGroupLoader.class)
public interface TagGroupLoaderAccessor {
    @Accessor
    public String getDataType();
}

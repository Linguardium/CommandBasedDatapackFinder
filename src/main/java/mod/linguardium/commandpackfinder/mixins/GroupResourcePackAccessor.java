package mod.linguardium.commandpackfinder.mixins;

import net.fabricmc.fabric.impl.resource.loader.GroupResourcePack;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GroupResourcePack.class)
public interface GroupResourcePackAccessor {
    @Accessor(remap = false)
    List<? extends ResourcePack> getPacks();
}
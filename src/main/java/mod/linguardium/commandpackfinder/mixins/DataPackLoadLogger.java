package mod.linguardium.commandpackfinder.mixins;

import com.google.gson.*;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.FunctionLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Mixin(FunctionLoader.class)
public class DataPackLoadLogger {
    @Shadow @Final private TagGroupLoader<CommandFunction> tagLoader;

    @Shadow @Final private static Logger LOGGER;

    @Shadow @Final private static ResourceFinder FINDER;

    @ModifyReturnValue(method="reload",at=@At("TAIL"))
    private CompletableFuture finallyCheckFunctionTags(CompletableFuture future, ResourceReloader.Synchronizer synchronizer, ResourceManager manager) {
        return future.thenRun(()->{
            String type = ((TagGroupLoaderAccessor)this.tagLoader).getDataType();
//            record functiondata(String pack, String when, String functionfile) {
//                @Override
//                public String toString() {
//                    return pack+"\t"+when+"\t"+functionfile;
//                }
//            }
//            List<functiondata> list = new ArrayList<>();
            LOGGER.info("---------------------------------");
            LOGGER.info("Listing McFunction triggers");
            LOGGER.info("---------------------------------");
            manager.streamResourcePacks().flatMap(pack->(pack instanceof GroupResourcePackAccessor grp)?grp.getPacks().stream(): Stream.of(pack)).forEach(pack->{
               pack.getNamespaces(ResourceType.SERVER_DATA).forEach(namespace->{
                  pack.findResources(ResourceType.SERVER_DATA,namespace,type,(identifier, inputStreamInputSupplier) -> {
                      LOGGER.info("{} / {}",pack.getName(),identifier);
//                      Maybe that will prevent crashing...
//                      try {
//                          InputStreamReader reader = new InputStreamReader(inputStreamInputSupplier.get());
//                          JsonElement element = JsonParser.parseReader(reader);
//                          JsonArray values = element.getAsJsonObject().getAsJsonArray("values");
//                          values.forEach(valueElement->{
//                              String s = valueElement.getAsString();
//                              if (s == null) s = "not_found";
//                              Identifier id = Identifier.tryParse(valueElement.getAsString());
//                              String path = "not_found";
//                              if (id != null) path = id.toString();
//                              list.add(new functiondata(pack.getName(),identifier.toString(),  path));
//                          });
//                          reader.close();
//
//                      } catch (IOException ignored) {
//                          // i don't care enough to deal with that
//                      }
                  });
               });
            });
//            LOGGER.info("---------------------------------");
//            LOGGER.info("Datapack\tRun Trigger\tFunction");
//            LOGGER.info("---------------------------------");
//            LOGGER.info("");
//            LOGGER.info("");
//            list.forEach(d->LOGGER.info(d.toString()));
//            LOGGER.info("");
//            LOGGER.info("");
            LOGGER.info("---------------------------------");
        });
    }
}

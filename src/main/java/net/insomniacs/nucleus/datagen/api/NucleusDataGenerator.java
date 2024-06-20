package net.insomniacs.nucleus.datagen.api;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.nucleus.datagen.impl.NucleusLanguageProvider;
import net.insomniacs.nucleus.datagen.impl.NucleusLootTableProvider;
import net.insomniacs.nucleus.datagen.impl.NucleusModelProvider;
import net.insomniacs.nucleus.datagen.impl.NucleusRecipeProvider;
import net.insomniacs.nucleus.datagen.impl.utility.IDMapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//TODO make this not ass,
// I cobbled something together to bind all the datagen into one abomination.
// I plan on fixing this.
public record NucleusDataGenerator(String modID, FabricDataGenerator generator, List<Class<?>> clazzes) {

    public static Map<Identifier, List<Annotation>> ANNOTATION_MAP;

    // Validate record
    public NucleusDataGenerator {
        Objects.requireNonNull(modID, "ModID cannot be null");
        Objects.requireNonNull(generator, "Generator cannot be null.");
        Objects.requireNonNull(clazzes, "Clazzes cannot be null.");
    }

    public NucleusDataGenerator(String modID, FabricDataGenerator generator) {
        this(modID, generator, List.of());
    }


    public void generate() {
        var pack = generator.createPack();

        ANNOTATION_MAP = IDMapper.mapAnnotations(clazzes);

        pack.addProvider((a,b) -> new NucleusLanguageProvider(a,b, this));
        pack.addProvider((a,b) -> new NucleusModelProvider(a, this));
        pack.addProvider((a,b) -> new NucleusLootTableProvider(a,b, this));
        pack.addProvider((a,b) -> new NucleusRecipeProvider(a,b,this));
    }

    public boolean filterMod(RegistryEntry.Reference<?> registryEntry) {
        var id = Identifier.of(registryEntry.getIdAsString());
        return Objects.equals(id.getNamespace(), modID);
    }

}

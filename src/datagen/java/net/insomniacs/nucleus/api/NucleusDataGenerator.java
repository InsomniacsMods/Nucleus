package net.insomniacs.nucleus.api;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.nucleus.impl.NucleusModelProvider;
import net.insomniacs.nucleus.impl.utility.IDMapper;
import net.insomniacs.nucleus.impl.NucleusLanguageProvider;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;

//TODO make this not ass,
// I cobbled something together to bind all the datagen into one abomination.
// I plan on fixing this.
public record NucleusDataGenerator(String modID, FabricDataGenerator generator, List<Class<?>> clazzes) {

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
        var idMap = IDMapper.mapAnnotations(clazzes);

        pack.addProvider((a,b) -> new NucleusLanguageProvider(a,b, this));
        pack.addProvider((a,b) -> new NucleusModelProvider(a, this));

    }

    public boolean filterMod(RegistryEntry.Reference<?> registryEntry) {
        var id = new Identifier(registryEntry.getIdAsString());
        return Objects.equals(id.getNamespace(), modID);
    }

}

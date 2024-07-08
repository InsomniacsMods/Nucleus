package net.insomniacs.nucleus.api.dataLoader;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class FileReader<Value> {

	public record FilePath(String basePath, Function<Identifier, Boolean> predicate) {
		public static FilePath simple(String path) {
			return new FilePath(path, p -> true);
		}
	}

	private final Function<BufferedReader, Value> bufferReader;

	public FileReader(Function<BufferedReader, Value> reader) {
		this.bufferReader = reader;
	}

	private final HashMap<Identifier, Consumer<Value>> getFiles = new HashMap<>();
	private final HashMap<FilePath, BiConsumer<Identifier, Value>> findFiles = new HashMap<>();

	void load(ResourceManager manager) {
		getFiles.forEach((id, consumer) -> getFile(manager, id, consumer));
		findFiles.forEach((path, consumer) -> findFiles(manager, path, consumer));
	}

	private void getFile(ResourceManager manager, Identifier id, Consumer<Value> consumer) {
		Optional<Resource> resource = manager.getResource(id);
		if (resource.isEmpty()) return;

		Value value = fromResource(resource.get());
		consumer.accept(value);
	}

	private void findFiles(ResourceManager manager, FilePath path, BiConsumer<Identifier, Value> consumer) {
		Map<Identifier, Resource> resources = manager.findResources(path.basePath(), path.predicate::apply);

		resources.forEach((identifier, resource) -> {
			Value value = fromResource(resource);
			consumer.accept(identifier, value);
		});
	}

	private Value fromResource(Resource resource) {
		try {
			return bufferReader.apply(resource.getReader());
		} catch (IOException ignored) {}
		return null;
	}

	public void get(Identifier file, Consumer<Value> consumer) {
		getFiles.put(file, consumer);
	}

	public void find(String path, BiConsumer<Identifier, Value> consumer) {
		findFiles.put(FilePath.simple(path), consumer);
	}

	public void findMatching(String basePath, Function<Identifier, Boolean> predicate, BiConsumer<Identifier, Value> consumer) {
		findFiles.put(new FilePath(basePath, predicate), consumer);
	}

	public void findGlob(String basePath, String glob, BiConsumer<Identifier, Value> consumer) {
		findMatching(basePath, identifier -> pathMatches(identifier, glob), consumer);
	}

	public void findGlob(String glob, BiConsumer<Identifier, Value> consumer) {
		findGlob("", glob, consumer);
	}

	private boolean pathMatches(Identifier id, String glob) {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:"+glob);
		Path path = Paths.get(id.getPath());
		return matcher.matches(path);
	}


}

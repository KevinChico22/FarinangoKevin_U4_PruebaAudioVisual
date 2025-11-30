package Repository;

import Model.*;
import Util.CSVHelper;
import java.nio.file.*;
import java.util.*;
import java.io.IOException;

public class FileContentRepository implements ContentRepository {
    private final Path path;

    public FileContentRepository(Path path) {
        this.path = path;
    }

    @Override
    public List<ContenidoAudioVisual> findAll() {
        List<ContenidoAudioVisual> result = new ArrayList<>();
        if (!Files.exists(path)) return result;
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                Optional<ContenidoAudioVisual> c = CSVHelper.parseLine(line);
                c.ifPresent(result::add);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo: " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void saveAll(List<ContenidoAudioVisual> contenidos) {
        List<String> lines = new ArrayList<>();
        for (ContenidoAudioVisual c : contenidos) lines.add(CSVHelper.toCSV(c));
        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo archivo: " + e.getMessage(), e);
        }
    }
}
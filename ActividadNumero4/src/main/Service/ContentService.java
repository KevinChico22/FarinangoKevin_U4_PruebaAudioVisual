 package Service;

import Model.ContenidoAudioVisual;
import Repository.ContentRepository;

import java.util.List;

public class ContentService {

    private final ContentRepository repository;

    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    // Obtiene todos los contenidos desde el repositorio
    public List<ContenidoAudioVisual> listarTodos() {
        return repository.findAll();
    }

    // Guarda la lista completa en el repositorio
    public void guardarTodos(List<ContenidoAudioVisual> lista) {
        repository.saveAll(lista);
    }
}

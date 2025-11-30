package Repository;

import Model.ContenidoAudioVisual;
import java.util.List;

public interface ContentRepository {
    List<ContenidoAudioVisual> findAll();
    void saveAll(List<ContenidoAudioVisual> contenidos);
}

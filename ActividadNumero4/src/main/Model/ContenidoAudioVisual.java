package Model;

public class ContenidoAudioVisual {
	  private String titulo;
	    private int anio;
	    private String director;

	    public ContenidoAudioVisual(String titulo, int anio, String director) {
	        this.titulo = titulo;
	        this.anio = anio;
	        this.director = director;
	    }

	    public String getTitulo() { return titulo; }
	    public int getAnio() { return anio; }
	    public String getDirector() { return director; }

	    @Override
	    public String toString() {
	        return String.format("%s (%d) - %s", titulo, anio, director);
	    }
	}

package co.edu.udistrital.mdp.caminatas.exceptions.http;

/*
 * Excepción que se lanza cuando en el proceso de búsqueda no se encuenta una entidad
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}


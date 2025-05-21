package co.com.fomag.lambda.content.application.services;

import co.com.fomag.lambda.content.adapter.in.lambda.dto.TipoDocumentoDto;
import co.com.fomag.lambda.content.application.domain.TipoDocumento;
import co.com.fomag.lambda.content.application.ports.in.execute.TipoDocumentoUseCase;
import co.com.fomag.lambda.content.infraestructure.database.TipoDocumentoRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del caso de uso para operaciones con tipos de documento.
 */
@Singleton
public class TipoDocumentoService implements TipoDocumentoUseCase { // Asumiendo que TipoDocumentoOperations es tu interfaz de servicio

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public List<TipoDocumentoDto> listarTodos() {
        List<TipoDocumento> entities = tipoDocumentoRepository.findAll();
        entities.stream().forEach(el-> System.out.println("Tipo documento:" + el.getNombreTipoDocumento()));

        return entities.stream()
                .map(el->new TipoDocumentoDto(el.getId(), el.getNombreTipoDocumento()))
                .collect(Collectors.toList());
    }
}

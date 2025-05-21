package co.com.fomag.lambda.content.application.services;


import co.com.fomag.lambda.content.application.domain.TipoDocumento;
import co.com.fomag.lambda.content.application.ports.in.execute.TipoDocumentoUseCase;
import co.com.fomag.lambda.content.infraestructure.database.TipoDocumentoRepository;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@Singleton
public class TipoDocumentoService implements TipoDocumentoUseCase {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(TipoDocumentoRepository repo) {
        this.tipoDocumentoRepository = repo;
    }

    public List<TipoDocumento> listarTodos() {
        log.info("Ejecutando listarTodos()");
        return tipoDocumentoRepository.findAll();
    }
}

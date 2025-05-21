package co.com.fomag.lambda.content.application.ports.in.execute;



import co.com.fomag.lambda.content.application.domain.TipoDocumento;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public interface TipoDocumentoUseCase {
    List<TipoDocumento> listarTodos();
}

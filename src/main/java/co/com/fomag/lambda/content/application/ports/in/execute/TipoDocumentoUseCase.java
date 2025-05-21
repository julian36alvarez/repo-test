package co.com.fomag.lambda.content.application.ports.in.execute;



import co.com.fomag.lambda.content.adapter.in.lambda.dto.TipoDocumentoDto;
import co.com.fomag.lambda.content.application.domain.TipoDocumento;

import java.util.List;

public interface TipoDocumentoUseCase {
    List<TipoDocumentoDto> listarTodos();
}

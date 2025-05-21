package co.com.fomag.lambda.content.adapter.in.lambda.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Serdeable
public class TipoDocumentoDto {

    private Long id;
    private String nombreTipoDocumento;

    public TipoDocumentoDto(co.com.fomag.lambda.content.application.domain.TipoDocumento entity) {
        this.id = entity.getId();
        this.nombreTipoDocumento = entity.getNombreTipoDocumento();
    }
}

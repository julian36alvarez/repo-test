package co.com.fomag.lambda.content.application.domain;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Accessors(fluent = false)
@MappedEntity(value = "tiposdocumento", schema = "parametrizacion")
public class TipoDocumento {

    @Id
    @MappedProperty("idtipodocumento")
    private Long id;

    @MappedProperty("nombretipodocumento")
    private String nombreTipoDocumento;

    public Long getId() {
        return id;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }
}

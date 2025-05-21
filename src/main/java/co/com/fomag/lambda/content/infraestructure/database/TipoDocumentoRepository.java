package co.com.fomag.lambda.content.infraestructure.database;

import co.com.fomag.lambda.content.application.domain.TipoDocumento;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface TipoDocumentoRepository extends CrudRepository<TipoDocumento, Long> {
}

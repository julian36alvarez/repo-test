package co.com.fomag.lambda.content.adapter.out.persistence.dynamodb.entities;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Setter
@Getter
@Introspected
@ReflectiveAccess
public class AdditionalHeaderEntity {

	private String name;

	private String value;

	public static final TableSchema<AdditionalHeaderEntity> TABLE_SCHEMA_HEADERS = TableSchema
			.builder(AdditionalHeaderEntity.class).newItemSupplier(AdditionalHeaderEntity::new)
			.addAttribute(String.class,
					a -> a.name("name").getter(AdditionalHeaderEntity::getName).setter(AdditionalHeaderEntity::setName))
			.addAttribute(String.class, a -> a.name("value").getter(AdditionalHeaderEntity::getValue)
					.setter(AdditionalHeaderEntity::setValue))
			.build();
}

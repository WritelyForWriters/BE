package com.writely.common.converter;

import com.writely.common.enums.Codable;
import jakarta.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractEnumCodeConverter<E extends Enum<E> & Codable> implements AttributeConverter<E, String> {

  @Override
  public String convertToDatabaseColumn(E attribute) {
    return this.toDatabaseColumn(attribute);
  }

  @Override
  public E convertToEntityAttribute(String dbData) {
    return null;
  }

  public E toEntityAttribute(Class<E> cls, String code) {
    return StringUtils.isBlank(code) ? null : Codable.fromCode(cls, code);
  }

  private String toDatabaseColumn(E attr) {
    return (attr == null) ? null : attr.getCode();
  }
}
package com.ourjupiter.springboot.domain.posts;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply=true)
public class FileUseConverter implements AttributeConverter<FileUse, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FileUse fileUse) {
        return fileUse.value;
    }

    @Override
    public FileUse convertToEntityAttribute(Integer dbData) {
        return Stream.of(FileUse.values())
                .filter(c -> c.value == dbData)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}

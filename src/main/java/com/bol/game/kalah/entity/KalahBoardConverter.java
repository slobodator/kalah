package com.bol.game.kalah.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class KalahBoardConverter implements AttributeConverter<KalahBoard, String> {
    private final String DELIMITER = " ";

    @Override
    public String convertToDatabaseColumn(KalahBoard kalahBoard) {
        return kalahBoard
                .getPits()
                .stream()
                .map(KalahPit::getStones)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public KalahBoard convertToEntityAttribute(String str) {
        return KalahBoard.init(
                Stream.of(str.split(DELIMITER))
                        .map(Integer::parseInt)
                        .toList()
        );
    }
}

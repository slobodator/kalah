package com.bol.game.kalah.entity;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.bol.game.kalah.entity.KalahGame.DEFAULT_STONES_PER_PIT;
import static com.bol.game.kalah.entity.KalahGame.PITS_AMOUNT;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class KalahBoard {
    private final List<Integer> pits;

    public KalahBoard(@Nullable Integer stonesPerPit) {
        this(
                Stream.concat(
                        Collections.nCopies(
                                Optional.ofNullable(stonesPerPit).orElse(DEFAULT_STONES_PER_PIT),
                                PITS_AMOUNT
                        ).stream(),
                        Stream.of(0)
                ).toList()
        );
    }

    public KalahBoard() {
        this(DEFAULT_STONES_PER_PIT);
    }


    public static KalahBoard init(List<Integer> pits) {
        return new KalahBoard(pits);
    }

    public int kalahPit() {
        return pits.get(PITS_AMOUNT);
    }
}

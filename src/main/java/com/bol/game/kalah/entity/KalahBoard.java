package com.bol.game.kalah.entity;

import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Stream;

import static com.bol.game.kalah.entity.KalahGame.DEFAULT_STONES_PER_PIT;
import static com.bol.game.kalah.entity.KalahGame.PITS_AMOUNT;

@Slf4j
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class KalahBoard {
    @EqualsAndHashCode.Include
    @ToString.Include
    private final List<Integer> pits = new ArrayList<>();

    private KalahBoard opponentsBoard;

    public KalahBoard(@Nullable Integer stonesPerPit) {
        pits.addAll(
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

    private KalahBoard(List<Integer> pits) {
        this.pits.addAll(pits);
    }

    public static KalahBoard init(List<Integer> pits) {
        return new KalahBoard(pits);
    }

    public static KalahBoard init(Integer... pits) {
        return new KalahBoard(Arrays.asList(pits));
    }

    public int kalahPit() {
        return pits.get(PITS_AMOUNT);
    }

    /**
     * @return true if the turn is changed
     */
    protected boolean move(int pitIndex) {
        KalahBoard kalahBoard = this;
        int stones = kalahBoard.takeFromPit(pitIndex);

        if (stones == 0) {
            throw new KalahGameException(
                    "The pit %d is not allowed for the turn".formatted(pitIndex)
            );
        }

        while (stones > 0) {
            pitIndex++;
            if (pitIndex > PITS_AMOUNT) {
                pitIndex = 0;
                kalahBoard = kalahBoard.opponentsBoard;
                log.debug("Move to {}", kalahBoard);
            }
            if (kalahBoard.equals(this) || pitIndex < PITS_AMOUNT) {
                int amount = kalahBoard.increasePit(pitIndex);
                log.debug("Increasing the {} th pit", pitIndex);

                if (kalahBoard.equals(this) && pitIndex < PITS_AMOUNT && amount == 1 && stones == 1) {
                    this.increasePit(
                            PITS_AMOUNT,
                            kalahBoard.takeFromPit(pitIndex) +
                                    kalahBoard.opponentsBoard.takeFromPit(PITS_AMOUNT - pitIndex - 1)
                    );
                }
            }
            stones--;
        }

        return pitIndex != PITS_AMOUNT;
    }

    private int takeFromPit(int pitIndex) {
        int stones = pits.get(pitIndex);
        pits.set(pitIndex, 0);
        log.debug("Take {} stones from the {} th pit", stones, pitIndex);
        return stones;
    }

    private int increasePit(int index, int amount) {
        int sum = pits.get(index) + amount;
        pits.set(index, sum);
        return sum;
    }

    private int increasePit(int index) {
        return increasePit(index, 1);
    }

    protected void linkTo(KalahBoard opponentsBoard) {
        if (this.opponentsBoard == null) {
            this.opponentsBoard = opponentsBoard;
            this.opponentsBoard.linkTo(this);
        }
    }

    protected boolean isEmpty() {
        return pits
                .stream()
                .limit(PITS_AMOUNT)
                .allMatch(p -> p.equals(0));
    }

    protected int gatherAll() {
        int sum = 0;
        for (int i = 0; i < PITS_AMOUNT; i++) {
            sum += takeFromPit(i);
        }
        this.increasePit(PITS_AMOUNT, sum);
        return sum;
    }
}

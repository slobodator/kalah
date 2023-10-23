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

    private KalahBoard opponentBoard;

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

    public int getStorePit() {
        return pits.get(PITS_AMOUNT);
    }

    /**
     * @return true if the turn is changed
     */
    protected boolean move(int pitIndex) {
        KalahBoard board = this;
        int stones = board.pickPitUp(pitIndex);

        if (stones == 0) {
            throw new KalahGameException(
                    "The pit %d is not allowed for the turn".formatted(pitIndex)
            );
        }

        while (stones > 0) {
            // next pit
            pitIndex++;

            // swithces to the opponents board
            if (pitIndex > PITS_AMOUNT) {
                board = board.opponentBoard;
                pitIndex = 0;
                log.debug("Switches to the opponent board {}", board);
            }

            // sow a stone to the pit if it not a kalah pit
            if (board.equals(this) || pitIndex < PITS_AMOUNT) {
                int amount = board.increasePit(pitIndex);

                // capture the opponent pit
                if (board.equals(this) && pitIndex < PITS_AMOUNT && amount == 1 && stones == 1) {
                    int opponentPitIndex = PITS_AMOUNT - pitIndex - 1;
                    this.increasePit(
                            PITS_AMOUNT,
                            board.pickPitUp(pitIndex) +
                                    board.opponentBoard.pickPitUp(opponentPitIndex)
                    );
                }
            }
            stones--;
        }

        @SuppressWarnings("UnnecessaryLocalVariable")
        boolean myTurn = pitIndex != PITS_AMOUNT;
        return myTurn;
    }

    private int pickPitUp(int pitIndex) {
        int stones = pits.get(pitIndex);
        pits.set(pitIndex, 0);
        log.debug("Take {} stones from the {} th pit", stones, pitIndex);
        return stones;
    }

    private int increasePit(int pitIndex, int amount) {
        log.debug("Increasing the {}th pit with {}", pitIndex, amount);
        int sum = pits.get(pitIndex) + amount;
        pits.set(pitIndex, sum);
        return sum;
    }

    private int increasePit(int index) {
        return increasePit(index, 1);
    }

    protected void linkTo(KalahBoard opponentsBoard) {
        if (this.opponentBoard == null) {
            this.opponentBoard = opponentsBoard;
            this.opponentBoard.linkTo(this);
        }
    }

    protected boolean isEmpty() {
        return pits
                .stream()
                .limit(PITS_AMOUNT)
                .allMatch(p -> p.equals(0));
    }

    protected int gatherAllStones() {
        int sum = 0;
        for (int i = 0; i < PITS_AMOUNT; i++) {
            sum += pickPitUp(i);
        }
        this.increasePit(PITS_AMOUNT, sum);
        return sum;
    }
}

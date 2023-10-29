package com.bol.game.kalah.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static com.bol.game.kalah.entity.KalahGame.PITS_AMOUNT;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@Getter
@EqualsAndHashCode
@ToString
public class KalahPit {
    private int index;
    private int stones;
    private boolean small;

    public static KalahPit smallOne(int index, int stones) {
        return new KalahPit(index, stones, true);
    }

    public static KalahPit bigOne(int stones) {
        return new KalahPit(PITS_AMOUNT, stones, false);
    }

    public int pickUp() {
        int temp = stones;
        log.debug("Take {} stones from the {}th pit", stones, index);
        stones = 0;
        return temp;
    }

    public int increase(int amount) {
        log.debug("Increasing the {}th pit with {}", index, amount);
        stones += amount;
        return stones;
    }

    public int increase() {
        return increase(1);
    }

    public boolean isEmpty() {
        return stones == 0;
    }

    public boolean isPlayable() {
        return index < PITS_AMOUNT;
    }
}

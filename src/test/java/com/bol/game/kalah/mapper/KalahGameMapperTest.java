package com.bol.game.kalah.mapper;

import com.bol.game.kalah.controller.response.KalahGameDto;
import com.bol.game.kalah.entity.KalahGame;
import com.bol.game.kalah.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KalahGameMapperTest {
    private final KalahGameMapper mapper = new KalahGameMapper(
            new PlayerMapperImpl(),
            new KalahBoardMapper()
    );

    @Test
    void mapping() {
        Player adamPlayer = new Player("adam@bol.com", "Adam");
        Player bobPlayer = new Player("bob@bol.com", "Bob");
        KalahGame kalahGame = new KalahGame(
                List.of(
                        adamPlayer,
                        bobPlayer
                )
        );

        KalahGameDto adamGameDto = mapper.toDto(kalahGame, adamPlayer);

        assertThat(adamGameDto.me().nickname())
                .isEqualTo("Adam");
        assertThat(adamGameDto.opponent().nickname())
                .isEqualTo("Bob");
        assertThat(adamGameDto.iAmWinner())
                .isNull();
        assertThat(adamGameDto.myTurn())
                .isEqualTo(true);
        assertThat(adamGameDto.inPlay())
                .isEqualTo(true);

        KalahGameDto bobGameDto = mapper.toDto(kalahGame, bobPlayer);

        assertThat(bobGameDto.me().nickname())
                .isEqualTo("Bob");
        assertThat(bobGameDto.opponent().nickname())
                .isEqualTo("Adam");
        assertThat(bobGameDto.iAmWinner())
                .isNull();
        assertThat(bobGameDto.myTurn())
                .isEqualTo(false);
        assertThat(bobGameDto.inPlay())
                .isEqualTo(true);

    }
}
package com.bol.game.kalah.mapper;

import com.bol.game.kalah.controller.response.PlayerDto;
import com.bol.game.kalah.entity.Player;
import org.mapstruct.Mapper;

@Mapper
public interface PlayerMapper {
    PlayerDto toDto(Player player);
}

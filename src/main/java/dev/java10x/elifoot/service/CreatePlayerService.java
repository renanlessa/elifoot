package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreatePlayerRequest;
import dev.java10x.elifoot.controller.response.PlayerResponse;
import dev.java10x.elifoot.entity.Player;
import dev.java10x.elifoot.mapper.PlayerMapper;
import dev.java10x.elifoot.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    private final FindClubService findClubService;

    public PlayerResponse execute(CreatePlayerRequest request) {
        Player player = playerMapper.toEntity(request);
        player.setClub(findClubService.findById(request.getClubId()));
        playerRepository.save(player);
        return playerMapper.toResponse(player);
    }
}

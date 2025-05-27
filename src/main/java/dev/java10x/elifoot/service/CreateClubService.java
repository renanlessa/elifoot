package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreateClubRequest;
import dev.java10x.elifoot.controller.response.ClubResponse;
import dev.java10x.elifoot.entity.Club;
import dev.java10x.elifoot.mapper.ClubMapper;
import dev.java10x.elifoot.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateClubService {

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    private final FindStadiumService findStadiumService;

    public ClubResponse execute(CreateClubRequest request) {
        Club newClub = clubMapper.toEntity(request);
        if (Objects.nonNull(newClub.getStadium())) {
            newClub.setStadium(findStadiumService.findById(newClub.getStadium().getId()));
        }

        newClub.setCreatedAt(LocalDateTime.now());
        newClub.setActive(true);

        Club club = clubRepository.save(newClub);
        return clubMapper.toResponse(club);
    }
}

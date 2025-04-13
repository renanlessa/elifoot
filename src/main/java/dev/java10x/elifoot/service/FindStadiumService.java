package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.response.StadiumResponse;
import dev.java10x.elifoot.mapper.StadiumMapper;
import dev.java10x.elifoot.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindStadiumService {

    private final StadiumRepository stadiumRepository;
    private final StadiumMapper stadiumMapper;

    public Page<StadiumResponse> execute(Pageable pageable) {
        return stadiumRepository.findAll(pageable)
                .map(stadiumMapper::toResponse);
    }
}

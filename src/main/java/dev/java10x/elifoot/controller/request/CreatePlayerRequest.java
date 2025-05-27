package dev.java10x.elifoot.controller.request;

import dev.java10x.elifoot.entity.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreatePlayerRequest {

    @NotBlank
    private String name;
    @NotNull
    private Position position;
    @NotNull
    private int shirtNumber;
    private String urlImg;
    @NotNull
    private Long clubId;
}

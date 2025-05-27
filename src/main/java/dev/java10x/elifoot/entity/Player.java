package dev.java10x.elifoot.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "player_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Position position;

    private int shirtNumber;

    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;


}

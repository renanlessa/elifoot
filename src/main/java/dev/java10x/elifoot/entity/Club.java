package dev.java10x.elifoot.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "club_seq")
    @SequenceGenerator(name = "club_seq", sequenceName = "club_seq", allocationSize = 1)
    private Long id;
    private String name;
    private LocalDate founded;
    private String urlImg;

    private LocalDateTime createdAt;
    private boolean active;

    @OneToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "club")
    private List<Player> players;
}

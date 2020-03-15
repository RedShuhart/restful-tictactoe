package com.iyushchuk.tictactoe.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "players")
public class Player {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_generator")
    @SequenceGenerator(name = "player_generator", sequenceName = "player_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "tag", nullable = false)
    private String tag;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @OneToMany(mappedBy = "xPlayer")
    private List<Game> xGames;

    @Getter
    @OneToMany(mappedBy = "oPlayer")
    private List<Game> yGames;

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
}

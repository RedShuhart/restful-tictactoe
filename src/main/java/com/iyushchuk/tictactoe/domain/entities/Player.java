package com.iyushchuk.tictactoe.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "players")
public class Player implements IEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_generator")
    @SequenceGenerator(name = "player_generator", sequenceName = "player_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "tag", updatable = false, unique = true, nullable = false)
    private String tag;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @OneToMany(mappedBy = "playerX")
    private List<Game> xGames;

    @Getter
    @OneToMany(mappedBy = "playerO")
    private List<Game> oGames;

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Player(String tag, String name) {
        this.tag = tag;
        this.name = name;
    }
}

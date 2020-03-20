package com.iyushchuk.tictactoe.domain.entities;


import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.util.UUIDGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "games")
public class Game implements IEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_generator")
    @SequenceGenerator(name = "game_generator", sequenceName = "game_seq" , allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(name = "tag", updatable = false, nullable = false)
    private String tag = UUIDGenerator.generateId();

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_x", referencedColumnName = "id", nullable = false)
    private Player playerX;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_o", referencedColumnName = "id", nullable = false)
    private Player playerO;

    @Setter
    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private Board board;


    @Setter
    @Getter
    @Enumerated
    @Column(name = "state", nullable = false)
    private GameState state = GameState.X_MOVE;

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

package com.iyushchuk.tictactoe.domain.entities;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "boards")
public class Board {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_generator")
    @SequenceGenerator(name = "board_generator", sequenceName = "board_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(name = "placements", nullable = false)
    private String placements;

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
}

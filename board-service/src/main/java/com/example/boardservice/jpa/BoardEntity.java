package com.example.boardservice.jpa;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@Entity
@Table(name="board")
@DynamicInsert
@DynamicUpdate
public class BoardEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNum;

    @Column(nullable = false, length = 1000)
    private String boardName;

    @Column(nullable = false, length = 1000)
    private String boardInfo;

    @Column(nullable = false)
    private Integer memberNum;

    @Column(nullable = false)
    private Integer teamNum;

    @ColumnDefault(value = "1")
    private Integer isLive;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date boardDate;
}

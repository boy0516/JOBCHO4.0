package com.example.postservice.jpa;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="post")
@DynamicInsert
@DynamicUpdate
public class PostEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNum;

    @Column(nullable = false, length = 1000)
    private String postTitle;

    @Column(nullable = false, length = 1000)
    private String postContexts;

    @Column(nullable = false)
    private Integer boardNum;

    @Column(nullable = false)
    private Integer memberNum;

    @ColumnDefault(value = "1")
    private Integer isLive;

    @ColumnDefault(value = "0")
    private Integer replyCnt; //댓글 수

    @Column(nullable = false)
    private String writer;//작성자

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date PostDate;

}

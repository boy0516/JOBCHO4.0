package com.example.replyservice.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reply")
@Getter @Setter
public class ReplyEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNum;

    @Column(nullable = false)
    private Integer memberNum;

    @Column(nullable = false)
    private Integer postNum;

    @Column(nullable = false, length = 1000)
    private String replyContents;

    @ColumnDefault(value = "1")
    private Integer islive;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date replyDate;

    @Column(nullable = false, length = 50)
    private String replyWriter;

}

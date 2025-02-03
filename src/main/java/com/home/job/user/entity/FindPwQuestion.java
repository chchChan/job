package com.home.job.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FindPwQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String question;
}

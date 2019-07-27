package com.upgrad.quora.service.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "answer")
public class AnswerEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

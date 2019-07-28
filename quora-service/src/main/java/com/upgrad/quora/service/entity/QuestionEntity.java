package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name ="QUESTION")
@NamedQueries(
        {
                @NamedQuery(name = "questionById", query = "select u from QuestionEntity u where u.id = :id"),
        })
public class QuestionEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "UUID")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "CONTENT")
    @NotNull
    @Size(max = 255)
    private String questionContent;

    @Column(name = "DATE")
    @NotNull
    private ZonedDateTime questionAt;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public ZonedDateTime getQuestionAt() {
        return questionAt;
    }

    public void setQuestionAt(ZonedDateTime questionAt) {
        this.questionAt = questionAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return id == that.id &&
                uuid.equals(that.uuid) &&
                questionContent.equals(that.questionContent) &&
                questionAt.equals(that.questionAt) &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, questionContent, questionAt, user);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}

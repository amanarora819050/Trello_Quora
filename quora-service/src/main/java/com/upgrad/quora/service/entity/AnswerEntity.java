package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name ="ANSWER")
@NamedQueries(
        {
                @NamedQuery(name = "answerById", query = "select u from AnswerEntity u where u.id = :id"),
        })
public class AnswerEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "UUID")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "ANS")
    @NotNull
    @Size(max = 255)
    private String answer;

    @Column(name = "DATE")
    private ZonedDateTime answerAt;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "QUESTION_ID")
    private QuestionEntity question;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ZonedDateTime getAnswerAt() {
        return answerAt;
    }

    public void setAnswerAt(ZonedDateTime answerAt) {
        this.answerAt = answerAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {

        this.user = user;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerEntity that = (AnswerEntity) o;
        return id == that.id &&
                uuid.equals(that.uuid) &&
                answer.equals(that.answer) &&
                answerAt.equals(that.answerAt) &&
                user.equals(that.user) &&
                question.equals(that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, answer, answerAt, user, question);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}

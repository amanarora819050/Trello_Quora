package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AnswerEntity createAnswer(AnswerEntity answerEntity) {

        entityManager.persist(answerEntity);
        return answerEntity;
    }

    public void updateQuestion(final QuestionEntity updatedQuestionEntity) {
        entityManager.merge(updatedQuestionEntity);
    }

    public AnswerEntity getAnswerById(final Integer id) {
        try {
            return entityManager.createNamedQuery("answerById", AnswerEntity.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AnswerEntity updateAnswer(final AnswerEntity updatedAnswerEntity) {
       return entityManager.merge(updatedAnswerEntity);
    }

    @Transactional
    public String deleteAnswerById(String answerUuId) {
        try {

            Query query=entityManager.createQuery("DELETE FROM AnswerEntity u WHERE u.uuid = :answerUuId");
            query.setParameter("answerUuId", answerUuId);
            int result= query.executeUpdate();



        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return answerUuId;
    }


}

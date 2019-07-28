package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionEntity getQuestionById(final Integer id) {
        try {
            return entityManager.createNamedQuery("questionById", QuestionEntity.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}

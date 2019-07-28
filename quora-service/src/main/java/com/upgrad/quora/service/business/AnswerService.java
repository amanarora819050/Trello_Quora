package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthTokenDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AnswerService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserAuthTokenDao userAuthTokenDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity createAnswer(String questionId,AnswerEntity answerEntity,String[] decodedAuth) throws AuthorizationFailedException, InvalidQuestionException {

        String userName=decodedAuth[0];
        UserEntity user= userDao.getUserByUsername(userName);
        UserAuthTokenEntity userAuthTokenEntity = userAuthTokenDao.getUserAuthTokenByUser(user);
        AnswerEntity answer=null;

        if(userAuthTokenEntity==null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        } else if(userAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to post an answers");
        } else {

            QuestionEntity questionEntity=questionDao.getQuestionById(Integer.parseInt(questionId));

            if(questionEntity == null){
                throw new InvalidQuestionException("QUES-001","The question entered is invalid");
            } else {

                //answerEntity.setQuestion(questionEntity);

                answerDao.updateQuestion(questionEntity);
                final ZonedDateTime now = ZonedDateTime.now();
                answerEntity.setQuestion(questionEntity);
                answerEntity.setAnswerAt(now);
                answerEntity.setUser(userAuthTokenEntity.getUser());

                answer=answerDao.createAnswer(answerEntity);

            }

        }

        return answer;

    }

}

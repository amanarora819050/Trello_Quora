package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthTokenDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
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
    public AnswerEntity createAnswer(String questionId,AnswerEntity answerEntity,String accessToken) throws AuthorizationFailedException, InvalidQuestionException {

        //String userName=decodedAuth[0];
        //UserEntity user= userDao.getUserByUsername(userName);
        UserAuthTokenEntity userAuthTokenEntity = userAuthTokenDao.getUserAuthTokenByAccessToken(accessToken);
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

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity editAnswerResp(String ansId,String accessToken,String newAnswerContent) throws AuthorizationFailedException,AnswerNotFoundException{

        UserAuthTokenEntity userAuthTokenEntity = userAuthTokenDao.getUserAuthTokenByAccessToken(accessToken);
        AnswerEntity answer=answerDao.getAnswerById(Integer.parseInt(ansId));
        AnswerEntity editedAns=null;

        if(userAuthTokenEntity==null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        } else if(userAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to edit an answer");
        } else if(answer==null){
            throw new AnswerNotFoundException("ANS-001","Entered answer uuid does not exist");
        } else{

            if(userAuthTokenEntity.getUser().getId()!=answer.getUser().getId()){
                throw new AuthorizationFailedException("ATHR-003","Only the answer owner can edit the answer");
            } else if(userAuthTokenEntity.getUser().getId()==answer.getUser().getId()){
                answer.setAnswer(newAnswerContent);
                editedAns=answerDao.updateAnswer(answer);

            }


        }

        return editedAns;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteAnswer(String answerId,String accessToken) throws AuthorizationFailedException,AnswerNotFoundException{

        UserAuthTokenEntity userAuthTokenEntity = userAuthTokenDao.getUserAuthTokenByAccessToken(accessToken);
        AnswerEntity answer = answerDao.getAnswerById(Integer.parseInt(answerId));
        String deletedAnswerUuid=null;
        if(answer==null){
            throw new AnswerNotFoundException("ANS-001","Entered answer uuid does not exist");
        } else if(userAuthTokenEntity==null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }else if(userAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to deletes an answer");
        } else if(!userAuthTokenEntity.getUser().getRole().equals("ADMIN") && answer.getUser().getId()!=userAuthTokenEntity.getUser().getId()){
            throw new AuthorizationFailedException("ATHR-003","Only the answer owner or admin can delete the answer");
        }else{
            deletedAnswerUuid=answerDao.deleteAnswerById(answer.getUuid());
        }

        return deletedAnswerUuid;
    }

}

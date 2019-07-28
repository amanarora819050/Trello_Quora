package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao ;

    public String  createQuestion(String question, UserEntity userEntity){
        QuestionEntity questionEntity =new QuestionEntity();
        questionEntity.setContent(question);
        questionEntity.setUserId(userEntity.getId());
        questionEntity.setUuid(UUID.randomUUID().toString());
        questionEntity.setDate(ZonedDateTime.now());


        questionDao.createQuestion(questionEntity);
        return questionEntity.getUuid();
    }

    public List<QuestionEntity> getAllQuestion(){
            return questionDao.getAllQuetion();

    }

    public boolean editQuestion(String questionId,String content){


        return questionDao.editQuestion(questionId,content);

    }
    public QuestionEntity getQuestion(String uuid){
        return questionDao.getQuestion(uuid);
    }

    public boolean deleteQuestion(String uuid){
        return questionDao.deleteQuestion(uuid);
    }

    public List<QuestionEntity> getAllQuestionBYUser(long uuid){
        return questionDao.getAllQuestionBYUser(uuid);
    }
}
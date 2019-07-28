package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.business.AuthenticationService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping(method= RequestMethod.POST, path="/question/{questionId}/answer/create", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(@RequestHeader("authorization") final String accessToken, final AnswerRequest answerRequest, @PathVariable("questionId") final String questionId) throws AuthorizationFailedException, InvalidQuestionException {

//        byte[] decode = Base64.getDecoder().decode(authorization);
//        String decodedText = new String(decode);
//        String[] decodedArray = decodedText.split(":");

        final AnswerEntity answerEntity = new AnswerEntity();

        answerEntity.setUuid(UUID.randomUUID().toString());
        answerEntity.setAnswer(answerRequest.getAnswer());

        AnswerEntity createdAnswerEntity = answerService.createAnswer(questionId,answerEntity,accessToken);

        AnswerResponse ansResp = new AnswerResponse();
        ansResp.setId(createdAnswerEntity.getUuid());
        ansResp.setStatus("ANSWER CREATED");


        return new ResponseEntity<AnswerResponse>(ansResp, HttpStatus.CREATED);

    }

    @RequestMapping(method= RequestMethod.PUT, path="/answer/edit/{answerId}", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> editAnswer(@PathVariable("answerId") String answerId, @RequestHeader("authorization") final String accessToken, AnswerEditRequest answerEditRequest) throws AuthorizationFailedException, AnswerNotFoundException {

        AnswerEntity editedAnswerEntity = answerService.editAnswerResp(answerId,accessToken,answerEditRequest.getContent());
        AnswerEditResponse ansEditResp = new AnswerEditResponse();

        ansEditResp.setId(editedAnswerEntity.getUuid());
        ansEditResp.setStatus("ANSWER EDITED");

        return new ResponseEntity<AnswerEditResponse>(ansEditResp,HttpStatus.OK);


    }

    @RequestMapping(method= RequestMethod.DELETE, path="/answer/delete/{answerId}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@PathVariable("answerId") String answerId, @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, AnswerNotFoundException {
        String deletedAnswerUuid = answerService.deleteAnswer(answerId,accessToken);

        AnswerDeleteResponse ansDelResp = new AnswerDeleteResponse();
        ansDelResp.setId(deletedAnswerUuid);
        ansDelResp.setStatus("ANSWER DELETED");

        return new ResponseEntity<AnswerDeleteResponse>(ansDelResp,HttpStatus.OK);


    }

}

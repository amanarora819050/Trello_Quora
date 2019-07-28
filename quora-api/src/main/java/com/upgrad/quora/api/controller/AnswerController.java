package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.business.AuthenticationService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping(method= RequestMethod.POST, path="/question/{questionId}/answer/create", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(@RequestHeader("authorization") final String authorization, final AnswerRequest answerRequest, @PathVariable("questionId") final String questionId) throws AuthorizationFailedException, InvalidQuestionException {

        byte[] decode = Base64.getDecoder().decode(authorization);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");

        final AnswerEntity answerEntity = new AnswerEntity();

        answerEntity.setUuid(UUID.randomUUID().toString());
        answerEntity.setAnswer(answerRequest.getAnswer());

        AnswerEntity createdAnswerEntity = answerService.createAnswer(questionId,answerEntity,decodedArray);

        AnswerResponse ansResp = new AnswerResponse();
        ansResp.setId(createdAnswerEntity.getUuid());
        ansResp.setStatus("ANSWER CREATED");


        return new ResponseEntity<AnswerResponse>(ansResp, HttpStatus.CREATED);

    }
}

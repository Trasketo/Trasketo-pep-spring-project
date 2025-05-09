package com.example.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;

import java.util.List;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    //private final AccountRepository accountRepository;
    //private final MessageRepository messageRepository;

    @Autowired
    public SocialMediaController(AccountService accountService , MessageService messageService){       
        this.accountService = accountService;
        this.messageService = messageService;
        
    }    

    @PostMapping("/register")
    public ResponseEntity<Account> registerNewAccount(@RequestBody Account account){
        Account saved = accountService.registerNewAccount(account);        
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account logAccount = accountService.loginAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(logAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message postMessage = messageService.submitMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(postMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retrieveAllMessages(){
        List<Message> messages = messageService.getAllMessages(); 
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> retrieveMessageById(@PathVariable ("messageId") Integer id){
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable ("messageId") Integer id){
        Integer rowAffected = messageService.deleteMessageById(id);
        return ResponseEntity.ok(rowAffected);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable("messageId") Integer id,@RequestBody Message message){
        Integer rowAffected = messageService.updateMessageTextById(id,message);
        return ResponseEntity.ok(rowAffected);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromUser(@PathVariable("accountId") Integer id){
        List<Message> messages = messageService.getAllMessagesFromUser(id);
        return ResponseEntity.ok(messages);
    }
}

package com.example.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
/**
 * Message Service should handle all message 
 */
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Checks if the message is valid if message {@code true} post {@code message}
     * @param message to be post
     * @return the data from the posted message
     */
    public Message submitMessage(Message message){          
        Optional<Account> author = accountRepository.findById(message.getPostedBy());        
        
        boolean isValid =
            message.getMessageText().length() <= 255 &&
            !message.getMessageText().isBlank() &&
            author.isPresent();
        if(!isValid){
            throw new InvalidMessageException("Something is wrong with the message");
        }
        return messageRepository.save(message);
    }

    /**
     * This method return all Messages in the database
     * @return a list of messages
     */
    public List<Message> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    /**
     * get message by id if isValid {@code true} if {@code false} returns empty
     * @param id message id
     * @return message
     */
    public Message getMessageById(Integer id){      
        return messageRepository.findById(id).orElse(null);
    }

    /**
     * delete message with giving messageId
     * @param id messageId 
     * @return number of row affected 
     */
    public Integer deleteMessageById(Integer id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }        

        return null;
    }

    /**
     * checks if message is valid if {@code true} update message text by id {@code else} 
     * @param id message to be update
     * @param message new message to update
     * @return number of affected rows
     */
    public Integer updateMessageTextById(Integer id, Message message){        
        Optional<Message> targetMessage = messageRepository.findById(id);
        boolean isValid = targetMessage.isPresent() &&
            !message.getMessageText().isBlank() && 
            message.getMessageText().length() <= 255 ;
        if(!isValid){
            throw new InvalidMessageException("Something is wrong with the message");
        }        
        return 1;
    }

    /**
     * retrieve all messages from given user 
     * @param id accoutId 
     * @return
     */
    public List<Message> getAllMessagesFromUser(Integer id){        
        return messageRepository.findAllByPostedBy(id);
    }
}

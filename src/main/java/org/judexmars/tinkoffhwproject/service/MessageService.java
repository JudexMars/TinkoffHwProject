package org.judexmars.tinkoffhwproject.service;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.model.Message;
import org.judexmars.tinkoffhwproject.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Message createMessage(String author, String content) {
        return messageRepository.save(Message.builder().author(author).content(content).build());
    }
}

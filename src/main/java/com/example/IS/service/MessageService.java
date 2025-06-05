package com.example.IS.service;

import com.example.IS.controller.form.MessageForm;
import com.example.IS.repository.MessageRepository;
import com.example.IS.repository.entity.Message;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*
     * 新規投稿登録処理（鈴木）
     */
    public void addMessage(MessageForm messageForm){
        //型をForm→Entityに変換する用メソッド
        Message message = setMessage(messageForm);

        //登録処理
        messageRepository.save(message);
    }

    private Message setMessage(MessageForm messageForm){
        Message message = new Message();
        message.setId(messageForm.getId());
        message.setTitle(messageForm.getTitle());
        message.setText(messageForm.getText());
        message.setCategory(messageForm.getCategory());
        message.setUserId(messageForm.getUserId());
        message.setCreatedDate(messageForm.getCreatedDate());
        message.setUpdatedDate(messageForm.getUpdatedDate());
        return message;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<MessageForm> setMessageForm(List<Message> results) {
//        ビューに返すためにはフォームに移し返さないといけない
        List<MessageForm> Messages = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            MessageForm message = new MessageForm();
            Message result = results.get(i);
            message.setId(result.getId());
            message.setTitle(result.getTitle());
            message.setText(result.getText());
            message.setCategory(result.getCategory());
            message.setUserId(result.getUserId());
            message.setCreatedDate(result.getCreatedDate());
            message.setUpdatedDate(result.getUpdatedDate());
            Messages.add(message);
        }
        return Messages;
    }
}

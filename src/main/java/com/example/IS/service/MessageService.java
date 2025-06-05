package com.example.IS.service;

import com.example.IS.controller.form.MessageForm;
import com.example.IS.dto.UserMessage;
import com.example.IS.repository.MessageRepository;
import com.example.IS.repository.entity.Message;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*
     * レコード絞り込み取得処理
     */
    public List<UserMessage> findMessageWithUserByOrder(LocalDate start, LocalDate end, String category) {
        final int LIMIT_NUM = 1000;

        // 未入力の場合、デフォルト値として"2022-01-01 00:00:00"を設定
        LocalDateTime startDateTime = (start != null) ? start.atStartOfDay() : LocalDate.of(2022, 1, 1).atStartOfDay();
        // 未入力の場合、現在日時を設定
        LocalDateTime endDateTime = (end != null) ? end.atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX);

        if (!StringUtils.isBlank(category)) {
            List<Object[]> results = messageRepository.findByCreatedDateBetweenAndCategoryOrderByCreatedDateAsc(startDateTime, endDateTime, category, LIMIT_NUM);
            return setUserMessage(results);
        } else {
            List<Object[]> results = messageRepository.findByCreatedDateBetweenOrderByCreatedDateAsc(startDateTime, endDateTime, LIMIT_NUM);
            return setUserMessage(results);
        }
    }

    /*
     * DBから取得したデータをDtoに設定
     */
    private List<UserMessage> setUserMessage(List<Object[]> results) {
        List<UserMessage> messages = new ArrayList<>();

        for (Object[] objects : results) {
            UserMessage message = new UserMessage();
            message.setId((Integer) objects[0]);
            message.setTitle((String) objects[1]);
            message.setText((String) objects[2]);
            message.setCategory((String) objects[3]);
            message.setUserId((Integer) objects[4]);
            message.setAccount((String) objects[5]);
            message.setName((String) objects[6]);
            message.setBranchId((Integer) objects[7]);
            message.setDepartmentId((Integer) objects[8]);
            message.setCreatedDate((LocalDateTime) objects[9]);
            message.setUpdatedDate((LocalDateTime) objects[10]);
            messages.add(message);
        }
        return messages;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<MessageForm> setMessageForm(List<Message> results) {
        List<MessageForm> messages = new ArrayList<>();

        for (Message value : results) {
            MessageForm message = new MessageForm();
            message.setId(value.getId());
            message.setTitle(value.getTitle());
            message.setText(value.getText());
            message.setCategory(value.getCategory());
            message.setUserId(value.getUserId());
            message.setCreatedDate(value.getCreatedDate());
            message.setUpdatedDate(value.getUpdatedDate());
            messages.add(message);
        }
        return messages;
    }
}

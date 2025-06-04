//package com.example.IS.service;
//
//import com.example.IS.repository.MessageRepository;
//import io.micrometer.common.util.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class MessageService {
//    @Autowired
//    MessageRepository messageRepository;
//
//    /*
//     * 新規投稿登録処理（鈴木）
//     */
//    public void addMessage(MessageForm messageForm){
//        //型をForm→Entityに変換する用メソッド
//        Message message = setMessage(messageForm);
//
//        //登録処理
//        messageRepository.save(message);
//    }
//
//    private Message setMessage(MessageForm messageForm){
//        Message message = new Message();
//        message.setId(messageForm.getId());
//        message.setTitle(messageForm.getTitle());
//        message.setText(messageForm.getText());
//        message.setCategory(messageForm.getCategory());
//        message.setUserId(messageForm.getUserId());
//        message.setCreatedDate(messageForm.getCreatedDate());
//        message.setUpdatedDate(messageForm.getUpdatedDate());
//        return message;
//    }
//    public List<MessageForm> findAllMessage(String start, String end, String category) throws ParseException {
//        //デフォルト値の設定
//        //全件取得
////        findAllで実行されている処理はSQL文の「select * from report;」のようなもの
////        日付をDate型に変換
//        String strStartDay;
//        String strEndDay;
//        if (!StringUtils.isBlank(start)) {
//            strStartDay = start + " 00:00:00";
//        } else {
//            strStartDay = "2020-01-01 00:00:00";    //デフォルト値
//        }
//        if (!StringUtils.isBlank(end)) {
//            strEndDay = end + " 23:59:59";
//
//        } else {
//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            strEndDay = sdf.format(date) + " 23:59:59";        //デフォルト値
//        }
//        //2つともDate型に変換する
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime startDay = LocalDateTime.parse(strStartDay,dtf);
//        LocalDateTime endDay = LocalDateTime.parse(strEndDay,dtf);
//
//
//        //ennity型
//        List<Message> results = messageRepository.findByCreatedDateBetweenAndCategoryOrderByCreatedDateDesc(startDay, endDay,category);
////        これはEntityはデータアクセス時の入れ物、FormはViewへの入出力時に使用する入れ物と役割を分けているためです
//        List<MessageForm> Messages = setMessageForm(results);
//        return Messages;
//    }
//    /*
//     * DBから取得したデータをFormに設定
//     */
//    private List<MessageForm> setMessageForm(List<Message> results) {
////        ビューに返すためにはフォームに移し返さないといけない
//        List<MessageForm> Messages = new ArrayList<>();
//
//        for (int i = 0; i < results.size(); i++) {
//            MessageForm message = new MessageForm();
//            Message result = results.get(i);
//            message.setId(result.getId());
//            message.setTitle(result.getTitle());
//            message.setText(result.getText());
//            message.setCategory(result.getCategory());
//            message.setUserId(result.getUserId());
//            message.setCreatedDate(result.getCreatedDate());
//            message.setUpdatedDate(result.getUpdatedDate());
//            Messages.add(message);
//        }
//        return Messages;
//    }
//
//    /*
//     * メッセージ削除
//     */
//    public void deleteMessage(Integer id) {
//        messageRepository.deleteById(id);
//    }
//}

package com.example.demo.Controller;

import com.example.demo.Dao.GroupDao;
import com.example.demo.model.Group;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@LineMessageHandler
public class HandleJoinGroupController {
    private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

    @EventMapping
    public TextMessage handleJoinNewGroup(JoinEvent joinEvent) {
        TextMessage replyMsg = null;
        String groupId = joinEvent.getSource().getSenderId();
        Source source = joinEvent.getSource();
        String group_id;
        if (source instanceof GroupSource) {
            group_id = ((GroupSource) source).getGroupId();
            System.out.println("GROUP ID : " + group_id);
        } else if (source instanceof RoomSource) {
            group_id = ((RoomSource) source).getRoomId();
            System.out.println("ROOM ID : " + group_id);
        }
        GroupDao.CreateTableData(groupId);
        replyMsg = new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /help untuk melihat command-command yang ada.");
        return replyMsg;
    }

    @EventMapping
    public TemplateMessage handleTextSlash(MessageEvent<TextMessageContent> msg) {
        String groupId = null;
        Source source = msg.getSource();
        Group group = new Group();
        if (source instanceof GroupSource) {
            groupId = ((GroupSource) source).getGroupId();
        } else if (source instanceof RoomSource) {
            groupId = ((RoomSource) source).getRoomId();
        }
        TemplateMessage templateMessage = null;
        PushMessage pushMessage;
        List<Message> messageList = new ArrayList<>();
        if (msg.getMessage().getText().toUpperCase().equals("/WOY")) {
            messageList.clear();
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn(null, "TUGAS", "yoi Tugas", Arrays.asList(
                                    new PostbackAction("Tambah Tugas",
                                            "/ADD-TUGAS"),
                                    new PostbackAction("Lihat Tugas",
                                            "/SHOW-TUGAS")
                            )),
                            new CarouselColumn(null, "UJIAN", "yoi Ujian", Arrays.asList(
                                    new PostbackAction("Tambah Ujian",
                                            "/ADD-UJIAN"),
                                    new PostbackAction("Lihat Ujian",
                                            "/SHOW-UJIAN")
                            ))
                    ));
            templateMessage = new TemplateMessage("LJ BOT mengirim pesan!", carouselTemplate);
//            TextMessage msgCommand = new TextMessage("Daftar command LJ BOT\n" +
//                    "1. /TUGAS [spasi] [deskripsi TUGAS]\n" +
//                    "2. /UJIAN [spasi] [deskripsi UJIAN]\n");
//            messageList.add(msgCommand);
//        } else if (msg.getMessage().getText().toUpperCase().substring(0,6).equals("/TUGAS")){
//            messageList.clear();
//            String deskripsi = msg.getMessage().getText().substring(7);
////            StickerMessage stickerSuksesTugas = new StickerMessage("1", "5");
////            messageList.add(stickerSuksesTugas);
//
////            //Add TUGAS ke database
//            group.setId("TUGAS" + "-" + deskripsi.substring(0,5));
//            group.setDeskripsi(deskripsi);
//            group.setTipe("tugas");
//            int status_insert = GroupDao.Insert(groupId, group);
//            if(status_insert==1)
//                System.out.println("Berhasil masukkan tugas ke database");
//            else
//                System.out.println("Gagal masukkan ke database");
//            List<Group> groupList = GroupDao.GetAll(groupId, "tugas");
//            StringBuilder Stringmsg = new StringBuilder();
//            int nomor=1;
//            for (Group groupp:groupList) {
//                Stringmsg.append(nomor + "." + "\nID : " + groupp.getId() + "\nTugas : " + groupp.getDeskripsi());
//                nomor++;
//            }
//            TextMessage msgAllTugas = new TextMessage("LIST TUGAS\n" + String.valueOf(Stringmsg));
//            messageList.add(msgAllTugas);
//        } else if(msg.getMessage().getText().substring(0,6).equals("/UJIAN")){
//            messageList.clear();
//            String deskripsi = msg.getMessage().getText().substring(7);
//            group.setId("UJIAN" + "-" + deskripsi.substring(0,5));
//            group.setDeskripsi(deskripsi);
//            group.setTipe("ujian");
//            int status_insert = GroupDao.Insert(groupId, group);
//            if(status_insert==1)
//                System.out.println("Berhasil masukkan tugas ke database");
//            else
//                System.out.println("Gagal masukkan ke database");
//            List<Group> groupList = GroupDao.GetAll(groupId, "ujian");
//            StringBuilder Stringmsg = new StringBuilder();
//            int nomor=1;
//            for (Group groupp:groupList) {
//                Stringmsg.append(nomor + "." + "\nID : " + groupp.getId() + "\nUjian : " + groupp.getDeskripsi());
//                nomor++;
//            }
//            TextMessage msgAllTugas = new TextMessage("LIST UJIAN\n" + String.valueOf(Stringmsg));
//            messageList.add(msgAllTugas);
//        }
//            pushMessage = new PushMessage(groupId, messageList);
//            Response<BotApiResponse> response =
//                    null;
//            try {
//                response = LineMessagingServiceBuilder
//                        .create(AccessToken)
//                        .build()
//                        .pushMessage(pushMessage)
//                        .execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(response.code() + " " + response.message());
        }
        return templateMessage;
    }

    @EventMapping
    public void handlePostback(PostbackEvent event){
        String userId = event.getSource().getUserId();
        String data = event.getPostbackContent().getData();
        TextMessage msg = null;
        if(data.equals("/ADD-TUGAS"))
            msg = new TextMessage("ADD TUGAS CLICKED!");
        PushMessage pushMessage = new PushMessage(userId, msg);
        Response<BotApiResponse> response = null;
        try {
            response = LineMessagingServiceBuilder
                    .create(AccessToken)
                    .build()
                    .pushMessage(pushMessage)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.code() + " " + response.message());
    }
}

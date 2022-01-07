package com.example.chatservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
@ServerEndpoint("/chatsocket/{chatroom_num}")
public class ChatSocketController {

    // 접속 된 클라이언트 WebSocket session 관리 Map
    private static Map<Integer, List<Session>> sessionMap = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void handleOpen(Session userSession, @PathParam("chatroom_num") int chatroom_num) {
        // 클라이언트가 접속하면 WebSocket세션을 리스트에 저장한다.
        if(sessionMap.get(chatroom_num)==null){
            sessionMap.put(chatroom_num, Collections.synchronizedList(new ArrayList<>()));
        }
        sessionMap.get(chatroom_num).add(userSession);

        // 콘솔에 접속 로그를 출력한다.
        System.out.println("client is now connected...");

        try {
            // 리스트에 있는 모든 세션(메시지 보낸 유저 제외)에 메시지를 보낸다. (형식: 유저명 => 메시지)
            userSession.getBasicRemote().sendText("연결");
        } catch (IOException e) {
            // 에러가 발생하면 콘솔에 표시한다.
            e.printStackTrace();
        }
    }

    // WebSocket으로 메시지가 오면 요청되는 함수
    @OnMessage
    public void handleMessage(String message, Session userSession,
                              @PathParam("chatroom_num") int chatroom_num) throws IOException {
        System.out.println(chatroom_num);
        System.out.println(message);
        // session관리 리스트에서 Session을 취득한다.
        sessionMap.get(chatroom_num).forEach(session -> {
            // 리스트에 있는 세션과 메시지를 보낸 세션이 같으면 메시지 송신할 필요없다.
            if (session == userSession) {
                return;
            }
            try {
                // 리스트에 있는 모든 세션(메시지 보낸 유저 제외)에 메시지를 보낸다. (형식: 유저명 => 메시지)
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                // 에러가 발생하면 콘솔에 표시한다.
                e.printStackTrace();
            }
        });
    }

    // WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
    @OnClose
    public void handleClose(Session userSession, @PathParam("chatroom_num") int chatroom_num) {
        // session 리스트로 접속 끊은 세션을 제거한다.
        sessionMap.get(chatroom_num).remove(userSession);
        if(sessionMap.get(chatroom_num).size()==0){
            sessionMap.remove(chatroom_num);
        }
        // 콘솔에 접속 끊김 로그를 출력한다.
        System.out.println("client is now disconnected...");
    }
}


package com.priyanshumaurya8868.room

import com.priyanshumaurya8868.data.MessageDataSource
import com.priyanshumaurya8868.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {

    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username))
            throw MemberAlreadyExistsException()

        members[username] = Member(
            username = username,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(sendername: String, msg: String) {
        //distribute msg to every  member
        members.values.forEach { member ->
            //create msg
            val messageEntity = Message(
                message = msg,
                username = sendername,
                timeStamp = System.currentTimeMillis()
            )
            //insert into  db
            messageDataSource.insertMessages(messageEntity)
            //parsed in to json
            val parsedMessage = Json.encodeToString(messageEntity)
            //send to each mem
            member.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getMessages()
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username))
            members.remove(username)
    }

}
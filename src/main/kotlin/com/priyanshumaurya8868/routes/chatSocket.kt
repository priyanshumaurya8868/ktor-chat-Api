package com.priyanshumaurya8868.routes

import com.priyanshumaurya8868.room.MemberAlreadyExistsException
import com.priyanshumaurya8868.room.RoomController
import com.priyanshumaurya8868.session.ChatSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import io.netty.handler.codec.http.HttpStatusClass
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(roomController: RoomController) {
    webSocket("/chat-socket") {
        val  session = call.sessions.get<ChatSession>()
        if (session ==null){
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY,"No Session found...!"))
            return@webSocket
        }
        try {
            roomController.onJoin(
                username = session.username,
                sessionId = session.sessionId,
                socket = this
            )
            //whenever there is message directed to thus socket this blog of code will execute
            incoming.consumeEach { frame ->
                if (frame is Frame.Text){
                    roomController.sendMessage(
                        sendername = session.username,
                        msg  = frame.readText()
                    )
                }
            }
        }catch (e : MemberAlreadyExistsException){
            call.respond(HttpStatusCode.Conflict)
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            roomController.tryDisconnect(session.username)
        }
    }
}

fun Route.getAllMessages(roomController: RoomController){
    get("/messages"){
        call.respond(HttpStatusCode.OK,roomController.getAllMessages())
    }
}
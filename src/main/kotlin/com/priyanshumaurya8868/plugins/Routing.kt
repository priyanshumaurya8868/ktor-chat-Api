package com.priyanshumaurya8868.plugins

import com.priyanshumaurya8868.room.RoomController
import com.priyanshumaurya8868.routes.chatSocket
import com.priyanshumaurya8868.routes.getAllMessages
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject


fun Application.configureRouting() {
    val roomController by inject<RoomController>(RoomController::class.java)
    install(Routing) {
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}

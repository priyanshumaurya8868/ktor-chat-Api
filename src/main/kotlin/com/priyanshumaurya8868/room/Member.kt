package com.priyanshumaurya8868.room

import io.ktor.websocket.*
import io.netty.handler.codec.http.websocketx.WebSocketScheme
import java.net.http.WebSocket

data class Member(
    val username : String,
    val sessionId : String,
    val socket : WebSocketSession
)

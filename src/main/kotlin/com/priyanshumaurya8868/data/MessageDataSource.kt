package com.priyanshumaurya8868.data

import com.priyanshumaurya8868.data.model.Message

interface MessageDataSource {

    suspend fun getMessages() : List<Message>

    suspend fun insertMessages(msg :Message)


}
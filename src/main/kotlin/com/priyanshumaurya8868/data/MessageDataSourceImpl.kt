package com.priyanshumaurya8868.data

import com.priyanshumaurya8868.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageDataSourceImpl(
    private val db : CoroutineDatabase
) : MessageDataSource {

    private val message = db.getCollection<Message>()

    override suspend fun getMessages(): List<Message> {
       return message.find()
            .descendingSort(Message::timeStamp)
            .toList()
    }

    override suspend fun insertMessages(msg: Message) {
       message.insertOne(msg)
    }
}
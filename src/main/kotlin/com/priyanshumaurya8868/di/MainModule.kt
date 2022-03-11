package com.priyanshumaurya8868.di


import com.priyanshumaurya8868.data.MessageDataSource
import com.priyanshumaurya8868.data.MessageDataSourceImpl
import com.priyanshumaurya8868.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase("ktorChat_db")
    }
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }
    single<RoomController> {
        RoomController(get())
    }
}

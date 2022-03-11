package com.priyanshumaurya8868

import com.priyanshumaurya8868.di.mainModule
import com.priyanshumaurya8868.plugins.*
import io.ktor.server.application.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    startKoin(koinApplication = KoinApplication.init()).modules(mainModule)
    configureSockets()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
}

package me.haroldmartin.intellijbuildwebhooknotifier

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

interface NotifierLocator {
    val buildNotifier: BuildNotifier
}

object ServiceLocator : NotifierLocator {
    private val httpClient  by lazy { HttpClient(CIO) }
    private val notifyUrl by lazy { NotifyUrlKtor(client = httpClient) }
    override val buildNotifier by lazy { WebhookBuildNotifier(notifyUrl = notifyUrl) }
}

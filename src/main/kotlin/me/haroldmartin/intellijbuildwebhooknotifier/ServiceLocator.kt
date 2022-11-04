package me.haroldmartin.intellijbuildwebhooknotifier

import com.intellij.openapi.diagnostic.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import me.haroldmartin.intellijbuildwebhooknotifier.network.BuildNotifier
import me.haroldmartin.intellijbuildwebhooknotifier.network.WebhookBuildNotifier

interface NotifierLocator {
    val buildNotifier: BuildNotifier
}

object ServiceLocator : NotifierLocator {
    val logger by lazy { Logger.getInstance("Webhooks") }
    private val httpClient by lazy { HttpClient(CIO) }
    override val buildNotifier by lazy { WebhookBuildNotifier(httpClient = httpClient, logger = logger) }
}

package me.haroldmartin.intellijbuildwebhooknotifier.network

import com.intellij.openapi.diagnostic.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus
import me.haroldmartin.intellijbuildwebhooknotifier.model.HttpMethod
import me.haroldmartin.intellijbuildwebhooknotifier.settings.AppSettingsState

interface BuildNotifier {
    operator fun invoke(buildStatus: BuildStatus, projectName: String)
}

class WebhookBuildNotifier(private val httpClient: HttpClient, private val logger: Logger) : BuildNotifier {
    override fun invoke(buildStatus: BuildStatus, projectName: String) {
        CoroutineScope(Dispatchers.IO + Job()).launch {
            notifyUrl(buildStatus, projectName)
        }
    }

    private suspend fun notifyUrl(buildStatus: BuildStatus, projectName: String) {
        val settings: AppSettingsState = AppSettingsState.instance
        val webhook = when (buildStatus) {
            BuildStatus.SUCCESS -> settings.successWebhook
            BuildStatus.ERROR -> if (settings.uniqueError) settings.errorWebhook else settings.successWebhook
            BuildStatus.STARTED -> if (settings.uniqueStarting) settings.startingWebhook else settings.successWebhook
            BuildStatus.WARNING -> return
        }

        val url = if (webhook.url.trim().isNotEmpty()) webhook.url.substitute(buildStatus, projectName) else return

        val response: HttpResponse = when (webhook.method) {
            HttpMethod.GET -> httpClient.get(url)
            HttpMethod.POST -> httpClient.post(urlString = url) {
                webhook.contentType?.let { contentType(ContentType.parse(it)) }
                webhook.body?.let {
                    setBody(it.substitute(buildStatus, projectName))
                }
            }
        }

        logger.debug("${response.request.url} -> ${response.status}")
    }
}

private fun String.substitute(buildStatus: BuildStatus, projectName: String): String {
    return trim()
        .replace("\$project", projectName)
        .replace("\$status", buildStatus.name)
}

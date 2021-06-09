package me.haroldmartin.intellijbuildwebhooknotifier

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.FormUrlEncoded
import io.ktor.http.contentType
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus
import me.haroldmartin.intellijbuildwebhooknotifier.settings.AppSettingsState
import me.haroldmartin.intellijbuildwebhooknotifier.settings.HttpMethod

interface NotifyUrl {
    suspend operator fun invoke(buildStatus: BuildStatus, projectName: String)
}
class NotifyUrlKtor(private val client: HttpClient) : NotifyUrl {
    override suspend operator fun invoke(buildStatus: BuildStatus, projectName: String) {
        val settings: AppSettingsState = AppSettingsState.instance
        val webhook = when(buildStatus) {
            BuildStatus.SUCCESS -> settings.successWebhook
            else -> null
        }

        val url = if (webhook?.url?.isNotEmpty() == true) webhook.url.substitute(buildStatus, projectName) else return

        val response: HttpResponse = when(webhook.method) {
            HttpMethod.GET -> client.get(url)
            HttpMethod.POST -> client.post(urlString = url) {
                webhook.contentType?.let { contentType(ContentType.parse(it)) }
                webhook.body?.let { body =  it.substitute(buildStatus, projectName) }
            }
        }

        println(response.status)
        client.close()
    }
}

private fun String.substitute(buildStatus: BuildStatus, projectName: String): String {
    return replace("\$project", projectName)
        .replace("\$status", buildStatus.name)
}

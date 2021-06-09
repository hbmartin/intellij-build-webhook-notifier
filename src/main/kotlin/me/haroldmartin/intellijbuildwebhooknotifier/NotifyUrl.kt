package me.haroldmartin.intellijbuildwebhooknotifier

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus
import me.haroldmartin.intellijbuildwebhooknotifier.settings.AppSettingsState
import me.haroldmartin.intellijbuildwebhooknotifier.settings.model.HttpMethod

interface NotifyUrl {
    suspend operator fun invoke(buildStatus: BuildStatus, projectName: String)
}
class NotifyUrlKtor(private val client: HttpClient) : NotifyUrl {
    override suspend operator fun invoke(buildStatus: BuildStatus, projectName: String) {
        val settings: AppSettingsState = AppSettingsState.instance
        val webhook = when (buildStatus) {
            BuildStatus.SUCCESS -> settings.successWebhook
            else -> settings.successWebhook
        }

        val url = if (webhook?.url?.isNotEmpty() == true) webhook.url.substitute(buildStatus, projectName) else return
        println(url)

        val response = when (webhook.method) {
            HttpMethod.GET -> client.get<HttpResponse>(url)
            HttpMethod.POST -> client.post<HttpResponse>(urlString = url) {
                webhook.contentType?.let { contentType(ContentType.parse(it)) }
                webhook.body?.let { body = it.substitute(buildStatus, projectName) }
            }
        }

        println(response.status)
    }
}

private fun String.substitute(buildStatus: BuildStatus, projectName: String): String {
    return replace("\$project", projectName)
        .replace("\$status", buildStatus.name)
}

package me.haroldmartin.intellijbuildwebhooknotifier

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

interface NotifyUrl {
    suspend operator fun invoke(urlString: String)
}
class NotifyUrlKtor(private val client: HttpClient) : NotifyUrl {
    override suspend operator fun invoke(urlString: String) {
        val response = client.get<HttpResponse>("https://ktor.io/")
        println(response.status)
        client.close()
    }
}

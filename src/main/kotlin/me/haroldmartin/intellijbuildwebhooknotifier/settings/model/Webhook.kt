@file:Suppress("ParameterListWrapping")
package me.haroldmartin.intellijbuildwebhooknotifier.settings.model

data class Webhook(
    val method: HttpMethod = HttpMethod.GET,
    val url: String = "",
    val body: String? = null,
    val contentType: String? = null
)

enum class HttpMethod { GET, POST; }

enum class HttpContentType(val raw: String) {
    FORM("application/x-www-form-urlencoded"),
    JSON("application/json");

    companion object {
        fun find(raw: String?): HttpContentType? {
            return values().firstOrNull { it.raw == raw }
        }
    }
}

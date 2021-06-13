@file:Suppress("ParameterListWrapping")
package me.haroldmartin.intellijbuildwebhooknotifier.model

import com.intellij.util.xmlb.annotations.Tag

data class Webhook(
    @Tag val method: HttpMethod = HttpMethod.GET,
    @Tag val url: String = "",
    @Tag val body: String? = null,
    @Tag val contentType: String? = null,
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

package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "me.haroldmartin.intellijbuildwebhooknotifier.settings.AppSettingsState",
    storages = [Storage("WebhookNotifierPluginPlugin.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var successWebhook = Webhook()

    override fun getState(): AppSettingsState  = this

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AppSettingsState
            get() = ServiceManager.getService(AppSettingsState::class.java)
    }
}

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

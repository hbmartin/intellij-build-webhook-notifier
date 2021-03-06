package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import me.haroldmartin.intellijbuildwebhooknotifier.model.Webhook

@State(
    name = "me.haroldmartin.intellijbuildwebhooknotifier.settings.AppSettingsState",
    storages = [Storage("WebhookNotifierPluginPlugin.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var successWebhook = Webhook()
    var errorWebhook = Webhook()
    var startingWebhook = Webhook()
    var uniqueError: Boolean = false
    var uniqueStarting: Boolean = false

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) = XmlSerializerUtil.copyBean(state, this)

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }
}

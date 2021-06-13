package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Property
import com.intellij.util.xmlb.annotations.Tag
import me.haroldmartin.intellijbuildwebhooknotifier.model.HttpContentType
import me.haroldmartin.intellijbuildwebhooknotifier.model.HttpMethod
import me.haroldmartin.intellijbuildwebhooknotifier.model.Webhook

@State(
    name = "me.haroldmartin.intellijbuildwebhooknotifier.settings.AppSettingsState",
    storages = [Storage("WebhookNotifierPluginPlugin.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var successWebhook = Webhook()

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) {
        println("STATE: ${state.successWebhook}")
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getComponent(AppSettingsState::class.java)
    }
}

package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

// Provides controller functionality for application settings.
class AppSettingsConfigurable : Configurable {
    private var settingsComponent: AppSettingsComponent? = null

    override fun getDisplayName(): String = "Webhooks"

    override fun getPreferredFocusedComponent(): JComponent? {
        return settingsComponent?.preferredFocusedComponent
    }

    override fun createComponent(): JComponent? {
        settingsComponent = AppSettingsComponent()
        return settingsComponent?.panel
    }

    override fun isModified(): Boolean {
        val settings: AppSettingsState = AppSettingsState.instance
        return settingsComponent?.successWebhook?.webhookModel != settings.successWebhook
    }

    override fun apply() {
        settingsComponent?.let {
            println("SETTING : ${it.successWebhook.webhookModel}")
            AppSettingsState.instance.successWebhook = it.successWebhook.webhookModel
        }
    }

    override fun reset() {
        settingsComponent?.successWebhook?.webhookModel = AppSettingsState.instance.successWebhook
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}

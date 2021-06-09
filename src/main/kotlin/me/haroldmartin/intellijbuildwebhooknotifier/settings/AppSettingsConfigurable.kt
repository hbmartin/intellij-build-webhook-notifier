package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

// Provides controller functionality for application settings.
class AppSettingsConfigurable : Configurable {
    private var settingsComponent: AppSettingsComponent? = null

    override fun getDisplayName(): String = "AppSettings Configurable"

    override fun getPreferredFocusedComponent(): JComponent? {
        return settingsComponent?.preferredFocusedComponent
    }

    override fun createComponent(): JComponent? {
        settingsComponent = AppSettingsComponent()
        return settingsComponent?.panel
    }

    override fun isModified(): Boolean {
        val settings: AppSettingsState = AppSettingsState.instance
        return settingsComponent?.successWebhook != settings.successWebhook
    }

    override fun apply() {
        val settings: AppSettingsState = AppSettingsState.instance
        settings.successWebhook = settingsComponent!!.successWebhook
    }

    override fun reset() {
        val settings: AppSettingsState = AppSettingsState.instance
        settingsComponent?.successWebhook = settings.successWebhook
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}

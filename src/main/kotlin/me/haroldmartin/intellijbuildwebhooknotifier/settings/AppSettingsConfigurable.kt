package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

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
        return settingsComponent?.let {
            it.successWebhook.webhookModel != settings.successWebhook ||
                it.errorWebhook.webhookModel != settings.errorWebhook ||
                it.startingWebhook.webhookModel != settings.startingWebhook ||
                it.uniqueErrorCheckBox.isSelected != settings.uniqueError ||
                it.uniqueStartingCheckBox.isSelected != settings.uniqueStarting
        } ?: true
    }

    override fun apply() {
        settingsComponent?.let {
            with(AppSettingsState.instance) {
                successWebhook = it.successWebhook.webhookModel
                errorWebhook = it.errorWebhook.webhookModel
                startingWebhook = it.startingWebhook.webhookModel
                uniqueError = it.uniqueErrorCheckBox.isSelected
                uniqueStarting = it.uniqueStartingCheckBox.isSelected
            }
        }
    }

    override fun reset() {
        settingsComponent?.apply {
            setState(AppSettingsState.instance)
        }
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}

package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AppSettingsComponent {
    val panel: JPanel
    val successWebhook = WebhookView("Webhook")

    val preferredFocusedComponent: JComponent
        get() = successWebhook.preferredFocusedComponent



    init {
        panel = FormBuilder.createFormBuilder()
            .addWebhookView(successWebhook)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}

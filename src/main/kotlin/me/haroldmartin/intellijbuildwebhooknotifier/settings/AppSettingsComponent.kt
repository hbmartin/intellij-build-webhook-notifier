package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

private const val INSET = 20

class AppSettingsComponent {
    val panel: JPanel
    val successWebhook = WebhookView("Webhook")
    val errorWebhook = WebhookView("Error Webhook", INSET).apply { isVisible = false }
    val startingWebhook = WebhookView("Starting Webhook", INSET).apply { isVisible = false }
    val uniqueErrorCheckBox = JBCheckBox("Unique Error Webhook").hides(errorWebhook)
    val uniqueStartingCheckBox = JBCheckBox("Unique Starting Webhook").hides(startingWebhook)

    val preferredFocusedComponent: JComponent
        get() = successWebhook.preferredFocusedComponent

    init {
        panel = FormBuilder.createFormBuilder()
            .addWebhookView(successWebhook)
            .addComponent(uniqueErrorCheckBox)
            .addComponent(uniqueStartingCheckBox)
            .addWebhookView(errorWebhook)
            .addWebhookView(startingWebhook)
            .addComponent(JBLabel("Hint: you can use \$project and \$status variables in the URL or POST body"), INSET)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}

private fun JBCheckBox.hides(webhookView: WebhookView): JBCheckBox = apply {
    addActionListener { webhookView.isVisible = this.isSelected }
}

private fun FormBuilder.addWebhookView(webhookView: WebhookView): FormBuilder =
    webhookView.addToForm(this)

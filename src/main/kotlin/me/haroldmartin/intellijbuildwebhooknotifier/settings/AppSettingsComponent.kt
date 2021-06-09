package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.application.options.schemes.AbstractSchemeActions
import com.intellij.application.options.schemes.EditableSchemesCombo
import com.intellij.application.options.schemes.SchemesModel
import com.intellij.application.options.schemes.SimpleSchemesPanel
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.event.ActionEvent
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class AppSettingsComponent {
    val panel: JPanel
    private val successWebhookTextField = JBTextField().apply { toolTipText = "URL" }
    private val successWebhookMethod = ComboBox(HttpMethod.values())
    private val successWebhookBodyField = JBTextField().apply { toolTipText = "Body" }
    private val successWebhookContentTypeField = ComboBox(HttpContentType.values())

    val preferredFocusedComponent: JComponent
        get() = successWebhookTextField

    var successWebhook: Webhook
        get() = Webhook(
            url = successWebhookTextField.text,
            method = successWebhookMethod.item,
            body = successWebhookBodyField.text,
            contentType = successWebhookContentTypeField.item.raw
        )
        set(newWebhook) {
            successWebhookTextField.text = newWebhook.url
            successWebhookMethod.item = newWebhook.method
            successWebhookBodyField.text = newWebhook.body
            successWebhookBodyField.isVisible = successWebhookMethod.item != HttpMethod.GET
            HttpContentType.find(newWebhook.contentType)?.let {
                successWebhookContentTypeField.item = it
            }
            successWebhookContentTypeField.isVisible = successWebhookMethod.item != HttpMethod.GET
        }

    init {
        successWebhookMethod.addActionListener {
            successWebhookBodyField.isVisible = successWebhookMethod.item != HttpMethod.GET
            successWebhookContentTypeField.isVisible = successWebhookMethod.item != HttpMethod.GET
        }

        panel = FormBuilder.createFormBuilder()
            .addComponent(JBLabel("Success webhook: "))
            .addLabeledComponent(successWebhookMethod, successWebhookTextField, 1, false)
            .addLabeledComponent(successWebhookContentTypeField, successWebhookBodyField, 1, false)
            .addSeparator()
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}

package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import me.haroldmartin.intellijbuildwebhooknotifier.model.HttpContentType
import me.haroldmartin.intellijbuildwebhooknotifier.model.HttpMethod
import me.haroldmartin.intellijbuildwebhooknotifier.model.Webhook
import javax.swing.JComponent

class WebhookView(private val label: String) {
    val urlTextField = JBTextField().apply { toolTipText = "URL" }
    val methodComboBox = ComboBox(HttpMethod.values())
    val bodyTextField = JBTextField().apply { toolTipText = "Body" }
    val contentTypeComboBox = ComboBox(HttpContentType.values())

    val preferredFocusedComponent: JComponent
        get() = urlTextField

    var webhookModel: Webhook
        get() = Webhook(
            url = urlTextField.text,
            method = methodComboBox.item,
            body = bodyTextField.text,
            contentType = contentTypeComboBox.item.raw
        )
        set(newWebhook) {
            urlTextField.text = newWebhook.url
            methodComboBox.item = newWebhook.method
            bodyTextField.text = newWebhook.body
            bodyTextField.isVisible = methodComboBox.item != HttpMethod.GET
            HttpContentType.find(newWebhook.contentType)?.let {
                contentTypeComboBox.item = it
            }
            contentTypeComboBox.isVisible = methodComboBox.item != HttpMethod.GET
        }

    init {
        methodComboBox.addActionListener {
            bodyTextField.isVisible = methodComboBox.isNotGet
            contentTypeComboBox.isVisible = methodComboBox.isNotGet
        }
    }
}

internal fun FormBuilder.addWebhookView(webhookView: WebhookView): FormBuilder {
    return addComponent(JBLabel("Success webhook: "))
        .addLabeledComponent(webhookView.methodComboBox, webhookView.urlTextField, 1, false)
        .addLabeledComponent(webhookView.contentTypeComboBox, webhookView.bodyTextField, 1, false)
        .addSeparator()
}

private val ComboBox<HttpMethod>.isNotGet: Boolean
    get() = item != HttpMethod.GET

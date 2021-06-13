package me.haroldmartin.intellijbuildwebhooknotifier.settings

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import me.haroldmartin.intellijbuildwebhooknotifier.model.HttpContentType
import me.haroldmartin.intellijbuildwebhooknotifier.model.HttpMethod
import me.haroldmartin.intellijbuildwebhooknotifier.model.Webhook
import javax.swing.JComponent
import javax.swing.JSeparator

private const val INSET = 10

class WebhookView(label: String, private val topInset: Int = 0) {
    private val labelComponent = JBLabel(label)
    private val urlTextField = JBTextField().apply { toolTipText = "URL" }
    private val methodComboBox = ComboBox(HttpMethod.values())
    private val bodyTextField = JBTextField().apply { toolTipText = "Body" }
    private val contentTypeComboBox = ComboBox(HttpContentType.values())
    private val separatorComponent = JSeparator()

    val preferredFocusedComponent: JComponent
        get() = urlTextField

    var isVisible: Boolean
        get() = urlTextField.isVisible
        set(viz) {
            labelComponent.isVisible = viz
            urlTextField.isVisible = viz
            methodComboBox.isVisible = viz
            bodyTextField.isVisible = viz
            contentTypeComboBox.isVisible = viz
            separatorComponent.isVisible = viz
        }

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

    fun addToForm(builder: FormBuilder): FormBuilder {
        return builder.addComponent(labelComponent, topInset)
            .addLabeledComponent(methodComboBox, urlTextField, INSET, false)
            .addLabeledComponent(contentTypeComboBox, bodyTextField, 1, false)
            .addComponent(separatorComponent)
    }
}

private val ComboBox<HttpMethod>.isNotGet: Boolean
    get() = item != HttpMethod.GET

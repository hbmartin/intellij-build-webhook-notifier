package me.haroldmartin.intellijbuildwebhooknotifier

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus

interface BuildNotifier {
    operator fun invoke(buildStatus: BuildStatus, projectName: String)
}

class WebhookBuildNotifier(private val notifyUrl: NotifyUrl) : BuildNotifier {
    override fun invoke(buildStatus: BuildStatus, projectName: String) {
        CoroutineScope(Dispatchers.IO).launch { notifyUrl("doesnt matter") }
    }
}

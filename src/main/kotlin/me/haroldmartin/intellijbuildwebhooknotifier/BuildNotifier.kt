package me.haroldmartin.intellijbuildwebhooknotifier

import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus

interface BuildNotifier {
    operator fun invoke(buildStatus: BuildStatus, projectName: String)
}

class WebhookBuildNotifier(): BuildNotifier {
    override fun invoke(buildStatus: BuildStatus, projectName: String) {
        TODO("Not yet implemented")
    }

}

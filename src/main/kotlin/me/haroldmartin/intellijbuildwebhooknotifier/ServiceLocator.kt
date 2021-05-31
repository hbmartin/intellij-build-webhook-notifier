package me.haroldmartin.intellijbuildwebhooknotifier

interface NotifierLocator {
    val buildNotifier: BuildNotifier
}

object ServiceLocator: NotifierLocator {
    override val buildNotifier = WebhookBuildNotifier()
}

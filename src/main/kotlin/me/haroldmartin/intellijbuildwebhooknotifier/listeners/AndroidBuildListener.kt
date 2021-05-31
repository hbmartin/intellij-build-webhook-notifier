package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.android.ide.common.blame.Message
import com.android.tools.idea.gradle.project.build.GradleBuildContext
import com.android.tools.idea.project.AndroidProjectBuildNotifications
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.project.Project
import me.haroldmartin.intellijbuildwebhooknotifier.ServiceLocator
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus

class AndroidBuildListener(project: Project) : BaseComponent {
    private val buildNotifier = ServiceLocator.buildNotifier

    init {
        AndroidProjectBuildNotifications.subscribe(project) { ctx ->
            (ctx as? GradleBuildContext)?.run {
                buildNotifier(
                    buildStatus = when {
                        buildResult.isBuildSuccessful -> BuildStatus.SUCCESS
                        buildResult.getCompilerMessages(Message.Kind.WARNING).isNotEmpty() -> BuildStatus.WARNING
                        else -> BuildStatus.ERROR
                    },
                    projectName = project.name
                )
            }
        }
    }
}

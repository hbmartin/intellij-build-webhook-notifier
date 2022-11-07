package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.android.tools.idea.gradle.project.build.BuildContext
import com.android.tools.idea.gradle.project.build.GradleBuildListener
import com.android.tools.idea.gradle.project.build.invoker.GradleBuildInvoker
import com.intellij.openapi.project.Project
import me.haroldmartin.intellijbuildwebhooknotifier.ServiceLocator
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus
import com.android.tools.idea.gradle.project.build.BuildStatus as GradleBuildStatus
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus as WhStatus

class AndroidBuildListener(private val project: Project) : GradleBuildListener {
    private val buildNotifier = ServiceLocator.buildNotifier

    override fun buildExecutorCreated(request: GradleBuildInvoker.Request) = Unit

    override fun buildStarted(context: BuildContext) {
        buildNotifier(BuildStatus.STARTED, project.name)
    }

    override fun buildFinished(
        status: GradleBuildStatus,
        context: BuildContext?
    ) {
        buildNotifier(
            buildStatus = when (status) {
                GradleBuildStatus.SUCCESS -> WhStatus.SUCCESS
                GradleBuildStatus.CANCELED -> WhStatus.CANCELLED
                GradleBuildStatus.FAILED -> WhStatus.ERROR
            },
            projectName = project.name
        )
    }
}

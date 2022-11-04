package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.android.tools.idea.gradle.project.build.BuildContext
import com.android.tools.idea.gradle.project.build.GradleBuildListener
import com.android.tools.idea.gradle.project.build.GradleBuildState
import com.android.tools.idea.gradle.project.build.invoker.GradleBuildInvoker
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import me.haroldmartin.intellijbuildwebhooknotifier.ServiceLocator
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus
import com.android.tools.idea.gradle.project.build.BuildStatus as GradleBuildStatus
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus as WhStatus

class AndroidBuildListener(project: Project) : BaseComponent {
    private val buildNotifier = ServiceLocator.buildNotifier
    private val logger: Logger = ServiceLocator.logger

    init {
        GradleBuildState.subscribe(
            project,
            object : GradleBuildListener {
                override fun buildExecutorCreated(request: GradleBuildInvoker.Request) {
                    logger.warn("buildExecutorCreated")
                }

                override fun buildStarted(context: BuildContext) {
                    logger.warn("buildStarted")
                    buildNotifier(BuildStatus.STARTED, project.name)
                }

                override fun buildFinished(
                    status: GradleBuildStatus,
                    context: BuildContext?
                ) {
                    logger.warn("buildFinished: $status")
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
        )
    }
}

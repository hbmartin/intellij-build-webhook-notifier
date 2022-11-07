package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.intellij.openapi.project.Project
import com.intellij.task.ProjectTaskContext
import com.intellij.task.ProjectTaskListener
import com.intellij.task.ProjectTaskManager
import me.haroldmartin.intellijbuildwebhooknotifier.ServiceLocator
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus

class BuildListener(private val project: Project) : ProjectTaskListener {
    private val buildNotifier = ServiceLocator.buildNotifier

    override fun finished(result: ProjectTaskManager.Result) {
        buildNotifier(
            buildStatus = when {
                result.hasErrors() -> BuildStatus.ERROR
                result.isAborted -> BuildStatus.CANCELLED
                else -> BuildStatus.SUCCESS
            },
            projectName = project.name
        )
    }

    override fun started(context: ProjectTaskContext) {
        buildNotifier(BuildStatus.STARTED, project.name)
    }
}

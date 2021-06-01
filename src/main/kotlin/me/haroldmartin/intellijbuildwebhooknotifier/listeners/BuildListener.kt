package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.intellij.openapi.compiler.CompilationStatusListener
import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompilerTopics
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.project.Project
import com.intellij.task.ProjectTaskContext
import com.intellij.task.ProjectTaskListener
import com.intellij.task.ProjectTaskManager
import me.haroldmartin.intellijbuildwebhooknotifier.ServiceLocator
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus

class BuildListener(project: Project) : BaseComponent {
    private val buildNotifier = ServiceLocator.buildNotifier

    private val compilationStatusListener = object : CompilationStatusListener {
        override fun compilationFinished(aborted: Boolean, errors: Int, warnings: Int, compileContext: CompileContext) {
            buildNotifier(
                buildStatus = when {
                    errors > 0 -> BuildStatus.ERROR
                    warnings > 0 -> BuildStatus.WARNING
                    else -> BuildStatus.SUCCESS
                },
                projectName = project.name
            )
        }
    }

    private val projectTaskListener = object : ProjectTaskListener {
        override fun finished(result: ProjectTaskManager.Result) {
            if (result.isAborted || result.hasErrors()) {
                buildNotifier(
                    buildStatus = BuildStatus.ERROR,
                    projectName = project.name + " -- PTL"
                )
            }
        }

        override fun started(context: ProjectTaskContext) {
            buildNotifier(
                buildStatus = BuildStatus.STARTED,
                projectName = project.name + " -- PTL"
            )
        }
    }

    init {
        project.messageBus.connect().subscribe(CompilerTopics.COMPILATION_STATUS, compilationStatusListener)
        project.messageBus.connect().subscribe(ProjectTaskListener.TOPIC, projectTaskListener)
    }
}

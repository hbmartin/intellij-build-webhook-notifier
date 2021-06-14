package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.intellij.execution.ExecutionListener
import com.intellij.execution.ExecutionManager
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.compiler.CompilationStatusListener
import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompilerTopics
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.project.Project
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

    private val executionListener = object : ExecutionListener {
        override fun processStartScheduled(executorId: String, env: ExecutionEnvironment) {
            buildNotifier(
                buildStatus = BuildStatus.STARTED,
                projectName = project.name
            )
        }
    }

    init {
        project.messageBus.connect().subscribe(CompilerTopics.COMPILATION_STATUS, compilationStatusListener)
        project.messageBus.connect().subscribe(ExecutionManager.EXECUTION_TOPIC, executionListener)
    }
}

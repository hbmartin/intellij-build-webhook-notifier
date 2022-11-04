package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.intellij.execution.ExecutionListener
import com.intellij.execution.ExecutionManager
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.compiler.CompilationStatusListener
import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompilerTopics
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.task.ProjectTaskContext
import com.intellij.task.ProjectTaskListener
import com.intellij.task.ProjectTaskManager
import me.haroldmartin.intellijbuildwebhooknotifier.ServiceLocator
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus

class BuildListener(project: Project) : BaseComponent {
    private val buildNotifier = ServiceLocator.buildNotifier
    private val logger: Logger = ServiceLocator.logger

    private val compilationStatusListener = object : CompilationStatusListener {
        override fun compilationFinished(aborted: Boolean, errors: Int, warnings: Int, compileContext: CompileContext) {
            logger.warn("compilationStatusListener: compilationFinished, $aborted, $errors, $compileContext")
            buildNotifier(
                buildStatus = when {
                    errors > 0 -> BuildStatus.ERROR
                    aborted -> BuildStatus.CANCELLED
                    else -> BuildStatus.SUCCESS
                },
                projectName = project.name
            )
        }
    }

    private val executionListener = object : ExecutionListener {
        override fun processStartScheduled(executorId: String, env: ExecutionEnvironment) {
            logger.warn("processStartScheduled: $env")
            buildNotifier(
                buildStatus = BuildStatus.STARTED,
                projectName = project.name
            )
        }

        override fun processTerminated(
            executorId: String,
            env: ExecutionEnvironment,
            handler: ProcessHandler,
            exitCode: Int
        ) {
            logger.warn("processTerminated: $env, $exitCode")
        }
    }

    private val projectTaskListener = object : ProjectTaskListener {
        override fun finished(result: ProjectTaskManager.Result) {
            logger.warn("projectTaskListener: finished: $result")
        }

        override fun started(context: ProjectTaskContext) {
            logger.warn("projectTaskListener: started")
        }
    }

    init {
        val messages = project.messageBus.connect()

        messages.subscribe(CompilerTopics.COMPILATION_STATUS, compilationStatusListener)
        messages.subscribe(ExecutionManager.EXECUTION_TOPIC, executionListener)
        messages.subscribe(ProjectTaskListener.TOPIC, projectTaskListener)
    }
}

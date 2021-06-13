package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.intellij.execution.ExecutionListener
import com.intellij.execution.ExecutionManager
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
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

    private val executionListener = object : ExecutionListener {
        override fun processStartScheduled(executorId: String, env: ExecutionEnvironment) {
            println("processStartScheduled")
            buildNotifier(
                buildStatus = BuildStatus.STARTED,
                projectName = project.name + " -- EL"
            )
        }

        override fun processStarting(executorId: String, env: ExecutionEnvironment) {
            println("processStarting")
        }

        override fun processNotStarted(executorId: String, env: ExecutionEnvironment) {
            println("processNotStarted")
        }

        override fun processStarted(executorId: String, env: ExecutionEnvironment, handler: ProcessHandler) {
            println("processStarted")
        }

        override fun processTerminating(executorId: String, env: ExecutionEnvironment, handler: ProcessHandler) {
            processTerminating(env.runProfile, handler)
        }

        override fun processTerminated(
            executorId: String,
            env: ExecutionEnvironment,
            handler: ProcessHandler,
            exitCode: Int
        ) {
            processTerminated(env.runProfile, handler)
        }


        @Deprecated("use {@link #processTerminating(String, ExecutionEnvironment, ProcessHandler)}")
        override fun processTerminating(runProfile: RunProfile, handler: ProcessHandler) {
            println("processTerminating")
        }


        @Deprecated("use {@link #processTerminated(String, ExecutionEnvironment, ProcessHandler, int)}")
        override fun processTerminated(runProfile: RunProfile, handler: ProcessHandler) {
            println("processTerminated")
        }
    }

    init {
        project.messageBus.connect().subscribe(CompilerTopics.COMPILATION_STATUS, compilationStatusListener)
        project.messageBus.connect().subscribe(ExecutionManager.EXECUTION_TOPIC, executionListener)
    }
}

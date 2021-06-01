package me.haroldmartin.intellijbuildwebhooknotifier.listeners

import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompileTask
import me.haroldmartin.intellijbuildwebhooknotifier.ServiceLocator
import me.haroldmartin.intellijbuildwebhooknotifier.model.BuildStatus

class BeforeCompileListener : CompileTask {
    private val buildNotifier = ServiceLocator.buildNotifier

    override fun execute(context: CompileContext): Boolean {
        buildNotifier(buildStatus = BuildStatus.STARTED, projectName = context.project.name)
        return true
    }
}

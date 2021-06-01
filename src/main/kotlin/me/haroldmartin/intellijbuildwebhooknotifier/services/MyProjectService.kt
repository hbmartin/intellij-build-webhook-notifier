package me.haroldmartin.intellijbuildwebhooknotifier.services

import com.intellij.openapi.project.Project
import me.haroldmartin.intellijbuildwebhooknotifier.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

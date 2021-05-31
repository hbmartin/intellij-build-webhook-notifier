package me.haroldmartin.intellijbuildwebhooknotifier.services

import me.haroldmartin.intellijbuildwebhooknotifier.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

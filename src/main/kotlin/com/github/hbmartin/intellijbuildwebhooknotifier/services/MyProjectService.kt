package com.github.hbmartin.intellijbuildwebhooknotifier.services

import com.github.hbmartin.intellijbuildwebhooknotifier.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

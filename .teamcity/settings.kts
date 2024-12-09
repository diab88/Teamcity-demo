import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.12"

project {
    description = "Contains all other projects"

    features {
        buildReportTab {
            id = "PROJECT_EXT_1"
            title = "Code Coverage"
            startPage = "coverage.zip!index.html"
        }
    }

    cleanup {
        baseRule {
            all(days = 365)
            history(days = 90)
            preventDependencyCleanup = false
        }
    }

    subProject(TeamcityDemo)
}


object TeamcityDemo : Project({
    name = "Teamcity Demo"

    vcsRoot(TeamcityDemo_HttpsGithubComDiab88TeamcityDemoRefsHeadsMain)

    buildType(TeamcityDemo_Build)
})

object TeamcityDemo_Build : BuildType({
    name = "Build"

    vcs {
        root(TeamcityDemo_HttpsGithubComDiab88TeamcityDemoRefsHeadsMain)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object TeamcityDemo_HttpsGithubComDiab88TeamcityDemoRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/diab88/Teamcity-demo#refs/heads/main"
    url = "https://github.com/diab88/Teamcity-demo"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "diab88"
        password = "credentialsJSON:0ac597a3-4334-497c-8606-07f09665b2ae"
    }
})

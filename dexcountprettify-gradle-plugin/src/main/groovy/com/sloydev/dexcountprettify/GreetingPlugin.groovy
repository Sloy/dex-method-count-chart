package com.sloydev.dexcountprettify

import com.android.build.gradle.AppPlugin
import info.persistent.dex.Config
import info.persistent.dex.DexCountApi
import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def hasApp = project.plugins.withType(AppPlugin)
        if (!hasApp) {
            throw new IllegalStateException("'android' plugin required.")
        }

        project.task('hello') << {
            println "Hello from the GreetingPlugin"
        }

        project.task('count') << {
            //def apkOutput = variant.outputs.find { variantOutput -> variantOutput instanceof ApkVariantOutput }
            project.android.applicationVariants.all { variant ->
                if(variant.name.equals("debug")){
                    def outputApk = variant.outputs.find()
                    String outputApkFile = outputApk.outputFile.getPath()
                    println "Output apk: " + outputApkFile

                    Config dexcountConfig = new Config()
                    dexcountConfig.format = 'json'
                    dexcountConfig.output = 'none'
                    String countResultJson = new DexCountApi().generateReport(dexcountConfig, outputApkFile)

                    /*File outputBase = config.baseOutputDir
                    if (!outputBase) {
                        outputBase = new File(project.buildDir, "spoon")
                    }*/

                    File outputBase = new File(project.buildDir, "dexcount")
                    File outputFile = new File(outputBase, "data.js")
                    outputFile.parentFile.mkdirs()
                    outputFile << "var data = "
                    outputFile << countResultJson
                    outputFile << ";"

                    new ReportUtils(outputBase).copyResources()
                }
            }
            //println outputFile
        }
    }
}





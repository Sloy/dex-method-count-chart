package com.sloydev.dexcountprettify

import info.persistent.dex.Config
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import info.persistent.dex.DexCountApi;


class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('hello') << {
            println "Hello from the GreetingPlugin"
        }

        project.task('count') <<{
            //def apkOutput = variant.outputs.find { variantOutput -> variantOutput instanceof ApkVariantOutput }
            android.applicationVariants.all { variant ->
                if(variant.name.equals("debug")){
                    def output = variant.outputs.find()
                    def outputFile = output.outputFile.getPath()
                    println "Output apk: " + outputFile

                    def config = new Config()
                    new DexCountApi().generateReport(config, outputFile)
                }
            }
            //println outputFile
        }
    }
}





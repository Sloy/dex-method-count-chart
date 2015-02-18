package com.sloydev.dexcountprettify;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class ReportUtils {

    private static final String[] STATIC_ASSETS = {"index.html", "Chart.js", "jquery-2.1.3.min.js"};
    private File outputDir;

    public ReportUtils(File outputDir) {
        this.outputDir = outputDir;
    }

    public void copyResources() {
        File statics = new File(outputDir, "");
        statics.mkdir();

        for (String staticAsset : STATIC_ASSETS) {
            copyStaticToOutput(staticAsset, statics);
        }
    }

    private static void copyStaticToOutput(String resource, File output) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = ReportUtils.class.getResourceAsStream("/static/" + resource);
            File outputFile = new File(output, resource);
            System.out.println(outputFile);
            os = new FileOutputStream(outputFile);
            IOUtils.copy(is, os);
        } catch (IOException e) {
            throw new RuntimeException("Unable to copy static resource " + resource + " to " + output+" due to: "+e.getMessage(), e);
        } finally {
            closeQuietly(is);
            closeQuietly(os);
        }
    }

    public static void closeQuietly(Closeable output) {
        try {
            if(output != null) {
                output.close();
            }
        } catch (IOException var2) {
        }

    }
}

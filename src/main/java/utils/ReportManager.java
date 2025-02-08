package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void setupReport() {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        File reportDir = new File("Report/");
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }

        String reportPath = "Report/ExtentReport_" + timestamp + ".html";
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Selenium Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static void startTest(String testName) {
        test = extent.createTest(testName);  
    }

    public static void logInfo(String message) {
        test.log(Status.INFO, message);
    }

    public static void logPass(String message) {
        test.log(Status.PASS, message);
    }

    public static void logFail(String message) {
        test.log(Status.FAIL, message);
    }

    public static void logPurchaseID(String purchaseID) {
        test.log(Status.INFO, "Purchase ID: " + purchaseID);
    }

    public static void endTest() {
        extent.flush();
    }
}

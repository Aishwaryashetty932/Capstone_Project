package utils;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.imageio.ImageIO;
import com.google.common.io.Files;

public class ScreenshotUtil {
    private static final String SCREENSHOT_FOLDER = "results/";

    // Method to capture and save a screenshot
    public static void takeScreenshot(WebDriver driver, String testName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File dest = new File(SCREENSHOT_FOLDER + testName + "_" + timestamp + ".png");
        Files.copy(src, dest);
    }

    public static void saveScreenshotsAsPDF(String testName) throws IOException {
        File folder = new File(SCREENSHOT_FOLDER);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Screenshot folder not found!");
            return;
        }

       
        File[] files = folder.listFiles((dir, name) -> name.startsWith(testName) && name.endsWith(".png"));

        if (files == null || files.length == 0) {
            System.out.println("No screenshots found for " + testName);
            return;
        }

        Arrays.sort(files, Comparator.comparing(File::getName));

    
        PDDocument document = new PDDocument();

        for (File file : files) {
            BufferedImage bImage = ImageIO.read(file);
            PDImageXObject pdImage = PDImageXObject.createFromFile(file.getAbsolutePath(), document);
            PDPage page = new PDPage(new PDRectangle(bImage.getWidth(), bImage.getHeight()));
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, 0, 0, bImage.getWidth(), bImage.getHeight());
            }
        }

        String pdfFilename = SCREENSHOT_FOLDER + testName + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
        document.save(pdfFilename);
        document.close();

        System.out.println("Screenshots saved as PDF: " + pdfFilename);
    }
}

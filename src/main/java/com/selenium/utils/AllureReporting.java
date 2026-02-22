package com.selenium.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * AllureReporting utility class for Allure report integration.
 * Handles screenshot capture and attachment to Allure reports.
 *
 * SOLID principles applied:
 * - Single Responsibility: Only manages Allure reporting functionality
 * - Open/Closed: Can be extended for additional reporting features
 * - Dependency Inversion: Depends on WebDriver abstraction
 *
 * @author Test Automation Team
 */
public class AllureReporting {

    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private AllureReporting() {
    }

    /**
     * Capture and attach a screenshot to the Allure report.
     *
     * @param driver the WebDriver instance
     * @param screenshotName the name for the screenshot
     */
    public static void attachScreenshot(WebDriver driver, String screenshotName) {
        Objects.requireNonNull(driver, "WebDriver cannot be null");
        Objects.requireNonNull(screenshotName, "Screenshot name cannot be null");

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String filename = String.format("%s_%s.png", screenshotName, LocalDateTime.now().format(DATE_FORMATTER));
            Allure.addAttachment(filename, "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.err.println("Failed to attach screenshot: " + e.getMessage());
        }
    }

    /**
     * Capture screenshot on test failure and attach to Allure report.
     *
     * @param driver the WebDriver instance
     * @param testName the name of the failed test
     */
    public static void captureScreenshotOnFailure(WebDriver driver, String testName) {
        Objects.requireNonNull(driver, "WebDriver cannot be null");
        Objects.requireNonNull(testName, "Test name cannot be null");

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String filename = String.format("FAILURE_%s_%s.png", testName, LocalDateTime.now().format(DATE_FORMATTER));
            Allure.addAttachment(filename, "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.err.println("Failed to capture failure screenshot: " + e.getMessage());
        }
    }

    /**
     * Save screenshot to file system and attach to Allure report.
     *
     * @param driver the WebDriver instance
     * @param screenshotName the name for the screenshot
     * @return the path to the saved screenshot file
     */
    public static String captureScreenshotToFile(WebDriver driver, String screenshotName) {
        Objects.requireNonNull(driver, "WebDriver cannot be null");
        Objects.requireNonNull(screenshotName, "Screenshot name cannot be null");

        try {
            // Create screenshot directory if it doesn't exist
            File screenshotDirectory = new File(SCREENSHOT_DIR);
            if (!screenshotDirectory.exists()) {
                screenshotDirectory.mkdirs();
            }

            // Capture screenshot as file
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filename = String.format("%s_%s.png", screenshotName, LocalDateTime.now().format(DATE_FORMATTER));
            String filepath = SCREENSHOT_DIR + File.separator + filename;
            File destinationFile = new File(filepath);

            // Copy file to destination
            org.apache.commons.io.FileUtils.copyFile(sourceFile, destinationFile);

            // Attach to Allure report
            attachScreenshotFromFile(destinationFile, filename);

            return filepath;
        } catch (IOException e) {
            System.err.println("Failed to save screenshot to file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Attach screenshot file to Allure report.
     *
     * @param screenshotFile the screenshot file
     * @param attachmentName the name for the attachment
     */
    public static void attachScreenshotFromFile(File screenshotFile, String attachmentName) {
        Objects.requireNonNull(screenshotFile, "Screenshot file cannot be null");
        Objects.requireNonNull(attachmentName, "Attachment name cannot be null");

        try (FileInputStream fileInputStream = new FileInputStream(screenshotFile)) {
            Allure.addAttachment(attachmentName, "image/png", fileInputStream, "png");
        } catch (IOException e) {
            System.err.println("Failed to attach screenshot from file: " + e.getMessage());
        }
    }

    /**
     * Add a text attachment to the Allure report.
     *
     * @param attachmentName the name for the attachment
     * @param content the text content
     */
    public static void attachText(String attachmentName, String content) {
        Objects.requireNonNull(attachmentName, "Attachment name cannot be null");
        Objects.requireNonNull(content, "Content cannot be null");

        Allure.addAttachment(attachmentName, "text/plain", content, "txt");
    }

    /**
     * Add HTML content as attachment to the Allure report.
     *
     * @param attachmentName the name for the attachment
     * @param htmlContent the HTML content
     */
    public static void attachHtml(String attachmentName, String htmlContent) {
        Objects.requireNonNull(attachmentName, "Attachment name cannot be null");
        Objects.requireNonNull(htmlContent, "HTML content cannot be null");

        Allure.addAttachment(attachmentName, "text/html", htmlContent, "html");
    }

    /**
     * Add page source to Allure report.
     *
     * @param driver the WebDriver instance
     */
    public static void attachPageSource(WebDriver driver) {
        Objects.requireNonNull(driver, "WebDriver cannot be null");

        try {
            String pageSource = driver.getPageSource();
            attachHtml("PageSource_" + LocalDateTime.now().format(DATE_FORMATTER), pageSource);
        } catch (Exception e) {
            System.err.println("Failed to attach page source: " + e.getMessage());
        }
    }

    /**
     * Add current URL to Allure report.
     *
     * @param driver the WebDriver instance
     */
    public static void attachCurrentUrl(WebDriver driver) {
        Objects.requireNonNull(driver, "WebDriver cannot be null");

        try {
            String currentUrl = driver.getCurrentUrl();
            attachText("CurrentURL_" + LocalDateTime.now().format(DATE_FORMATTER), currentUrl);
        } catch (Exception e) {
            System.err.println("Failed to attach current URL: " + e.getMessage());
        }
    }

    /**
     * Add complete debug information (screenshot, URL, page source) to Allure report.
     *
     * @param driver the WebDriver instance
     * @param testName the name of the test
     */
    public static void attachCompleteDebugInfo(WebDriver driver, String testName) {
        Objects.requireNonNull(driver, "WebDriver cannot be null");
        Objects.requireNonNull(testName, "Test name cannot be null");

        captureScreenshotOnFailure(driver, testName);
        attachCurrentUrl(driver);
        attachPageSource(driver);
    }
}

package com.automation.utils;

/**
 * AlertMessageParser - Utility for parsing alert messages
 */
public class AlertMessageParser {
    
    public static boolean isErrorAlert(String alertText) {
        return alertText != null && alertText.toLowerCase().contains("error");
    }
    
    public static boolean isSuccessAlert(String alertText) {
        return alertText != null && 
               (alertText.toLowerCase().contains("success") || 
                alertText.toLowerCase().contains("created") ||
                alertText.toLowerCase().contains("successfully"));
    }
    
    public static boolean isValidationAlert(String alertText) {
        return alertText != null && 
               (alertText.toLowerCase().contains("invalid") || 
                alertText.toLowerCase().contains("required") ||
                alertText.toLowerCase().contains("special character") ||
                alertText.toLowerCase().contains("not allowed"));
    }
    
    public static String extractMessage(String alertText) {
        return alertText != null ? alertText.trim() : "";
    }
}

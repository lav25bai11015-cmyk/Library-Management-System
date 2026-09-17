package utils;

import interfaces.Reportable;

public class ReportGenerator {

    public static void printReport(Reportable reportable) {
        System.out.println("\n========== LIBRARY REPORT ==========");
        System.out.println(reportable.generateReport());
        System.out.println("====================================");
    }
}
package org.jboss.arquillian.drone.qunit;

import java.net.URL;
import java.util.List;

public class QUnitResults {

    private final List<String> passedTestsNames;

    private final List<String> failedTestsNames;

    private final int totalTests;

    private final int failedTests;

    private final int passedTests;

    private final URL location;

    public QUnitResults(URL location, int totalTests, int failedTests, int passedTests, List<String> passedTestsNames,
            List<String> failedTestNames) {
        this.location = location;
        this.failedTests = failedTests;
        this.passedTests = passedTests;
        this.totalTests = totalTests;
        this.failedTestsNames = failedTestNames;
        this.passedTestsNames = passedTestsNames;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("QUnit tests on ").append(location).append(", ").append(passedTests)
                .append(" passed, ").append(failedTests).append(" failed, total ").append(totalTests);

        if (failedTests > 0) {
            sb.append("\nFailed QUnit tests were:\n * ");
            for (String testCase : failedTestsNames) {
                sb.append(testCase).append("\n * ");
            }
            if (sb.indexOf("\n * ") != -1) {
                sb.delete(sb.lastIndexOf("\n * "), sb.length());
            }
        }

        return sb.toString();
    }

    public boolean containsFailures() {
        return failedTests > 0;
    }

    public List<String> getPassedTestsNames() {
        return passedTestsNames;
    }

    public List<String> getFailedTestsNames() {
        return failedTestsNames;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public int getFailedTests() {
        return failedTests;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public URL getLocation() {
        return location;
    }

}

package org.example.tests.comments;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class CommentsListener implements ITestListener {
    private final Logger logger = Logger.getLogger(CommentsListener.class);

    public void onTestStart(ITestResult result) {
        logger.log(Level.INFO, "Starting: " + result.getName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.log(Level.INFO, "Test passed: " + result.getName());

        Object[] params = result.getParameters();
        logger.log(Level.INFO, "Params:\n" + Arrays.toString(params));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.log(Level.ERROR, "Test Failed: " + result.getName());

        Object[] params = result.getParameters();
        logger.log(Level.ERROR, "Params:\n" + Arrays.toString(params));
    }
}

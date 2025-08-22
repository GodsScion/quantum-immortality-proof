/*
 * Author: Sai Vignesh Golla
 * License: MIT
 * Email: saivigneshgolla@gmail.com
 * 
 * Disclaimer:
 * This program is provided for educational and theoretical purposes only.
 * The authors and contributors of this program are not liable for any direct or indirect consequences
 * arising from the interaction, use, misuse or any activity involving directly or indirectly of this program, or related programs, or related topics.
 * Use this program at your own risk.
 * This simulation is purely theoretical and the authors do not endorse any harmful behavior
 * or actions based on the concepts explored in this program.
 * 
 * Copyright (c) 2025 Sai Vignesh Golla
 * 
 * Version: 1.0
 * Date: Aug 22, 2025.
 */


import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class RunExperimentMainApp {
    private static final String LOG_FILE_PATH = "simulation_log.txt";
    private static final Logger logger = Logger.getLogger(RunExperimentMainApp.class.getName());
    private static FileHandler fileHandler;

    // Logs related methods
    private static Formatter getSingleLineFormatter() {
        return new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");
                String date = sdf.format(new Date(record.getMillis()));
                return String.format("%s - %s: %s%n", date, record.getLevel(), formatMessage(record));
            }
        };
    }

    private static void initLogs() {
        try {
            fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(getSingleLineFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Failed to initialize logs: " + e.getMessage());
        }
    }

    private static void logToFile(String message) {
        System.out.println(message);
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true)) {
            fileWriter.write(message + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Failed to log message: " + e.getMessage());
            logger.severe(e.getMessage());
        }
    }

    private static void logInfo(String messagePattern, Object... args) {
        logger.info(MessageFormat.format(messagePattern, args));
    }


    // Simulation methods
    // public static boolean tryLuck(float survivalProbability) {
    //     if (survivalProbability <= 0.0 || survivalProbability >= 1.0) {
    //         throw new IllegalArgumentException("Survival probability must be between 0 and 1.");
    //     }
    //     return Math.random() <= survivalProbability;
    // }

    public static boolean tryLuck() {
        // Math.random() is Pseudo-random, In need of something truly random/chaotic and efficient, until then...
        return Math.random() <= 0.2;
    }

    public static void main(String[] args) {
        long experimentStartTime = System.currentTimeMillis();

        // Make sure the log file is created before starting the simulation
        initLogs();
        logToFile("\n\n#################################################################################\n\n");
        
        // Number of trials to simulate. Higher, the better for proof, but also higher probability for failures, and exponential growth in time and storage consumption.
        long trials = 100;
        BigInteger simulationCount = BigInteger.ZERO;

        logInfo("Starting new simulation run. Each simulation consists of {0} trials.", trials);

        while (true) {
            long simulationStartTime = System.nanoTime();
            simulationCount = simulationCount.add(BigInteger.ONE);
            boolean survived = true;
            for (int i = 0; i < trials; i++) {
                if (!tryLuck()) {
                    survived = false;
                    logInfo("Simulation {0}. Survived {1} trials. Time elapsed: {2} ns", simulationCount, i, System.nanoTime() - simulationStartTime);
                    break;
                }
            }
            if (survived) {
                logInfo("Survived all {0} trials, which was a 0.2^{0} probability, this kind of proves the possibility for quantum immortality!", trials);
                logInfo("Total time elapsed for this simulation: {0} ns", System.nanoTime() - simulationStartTime);
                break;
            }
        }

    StringBuilder finalMsg = new StringBuilder();
    finalMsg.append("\n==================================================================================\n");
    finalMsg.append("Congratulations! You have successfully simulated the most rarest scenario that suggests quantum immortality.");
    finalMsg.append(MessageFormat.format("Your chances were 0.2^{0}. And you did it. Was this worth your time?\n", trials));
    finalMsg.append(MessageFormat.format("Total simulations run: {0}.\nTotal time elapsed: {1} ms\n", simulationCount, System.currentTimeMillis() - experimentStartTime)); // I don't think experiment would run for 292 million years, we should be good.
    finalMsg.append("This simulation is purely theoretical and should not be taken as a real-life proof of quantum immortality.\n");
    finalMsg.append("Always prioritize safety and well-being in real life over theoretical concepts.\n");
    finalMsg.append("Thank you for running this simulation!\n");
    finalMsg.append("Have a great day!\n");
    logToFile(finalMsg.toString());

    logger.info("Experiment ended. Exiting program. Thank you for participating!");

    logToFile("\n\n#################################################################################\n\n");
    System.exit(0);
    }
}

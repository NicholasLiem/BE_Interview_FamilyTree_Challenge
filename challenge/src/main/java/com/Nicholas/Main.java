package com.Nicholas;

import com.Nicholas.commands.CommandManager;
import com.Nicholas.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath;

        if (args.length != 0){
            filePath = args[0];
        } else {
            filePath = getFilePath(scanner);
        }

        if (filePath != null) {
            processCommands(filePath);
        }

        scanner.close();
    }

    public static String getFilePath(Scanner scanner){
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println("Current Working Directory: " + currentWorkingDirectory);
        System.out.print("Enter the path to the text file: ");
        return scanner.nextLine();
    }

    public static void processCommands(String filePath) {
        try {
            CommandManager cm = CommandManager.getInstance();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean isFirstLine = true;


            while ((line = reader.readLine()) != null) {

                String[] inputStr = Utils.sanitizeInput(line);
                String command = inputStr[0];
                String[] arguments = new String[inputStr.length - 1];
                System.arraycopy(inputStr, 1, arguments, 0, arguments.length);
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    System.out.println();
                }
                cm.executeCommand(command, arguments);

            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the command file: " + e.getMessage());
        }
    }
}
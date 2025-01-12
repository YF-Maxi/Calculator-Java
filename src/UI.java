import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

class UI 
{
    Calculator calculator = new Calculator();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);
    boolean running = true;
    char operator;
    double value1;
    double value2;
    double ans;
    int timesToCalculate = 100;

    public void Run() throws IOException
    {
        while (running)
        {
            HandleInput();
            PrintResult();
        }
    }

    void HandleInput() throws IOException
    {
        System.out.println("Enter calcualtion with the format\nvalue1 {+ | - | * | /} value2");

        String input = reader.readLine();
        
        if (input.matches(".*[a-zA-Z]+.*")) // Checks if it contains letters
        {
            ClearConsole();
            System.out.println("Invalid input: Input contains letters. Please enter numbers and operators only.\n");
            HandleInput();
        }
        else if (input.contains(" ") == false) // Checks if it contains spaces
        {
            ClearConsole();
            System.out.println("Invalid input: Must contain spaces between the operator and numbers.\n");
            HandleInput();
        }
        else if (input.matches(".*[+\\-*/].*") && input.matches(".*\\d.*")) // Operator and number check, must contain one of the characters for the operation and a number
        {
            if (!SplitString(input))
            {
                ClearConsole();
                System.out.println("Parsing falied because of wrong format.\n");
                HandleInput();
            }
        }
        else
        {
            ClearConsole();
            System.out.println("Invalid input");
            HandleInput();
        }
    }

    boolean SplitString(String input)
    {
        String[] parts = input.split(" "); // The spaces are used to know where to split

        try
        {
            value1 = Double.parseDouble(parts[0]);
            operator = parts[1].charAt(0);
            value2 = Double.parseDouble(parts[2]);

            return true; // Parsing successful
        }
        catch (NumberFormatException e) // Catch the error in case it fails to convert something to a double
        {
            return false; // Parsing failed
        }
    }

    void PrintResult()
    {
        if (value2 == 0 && operator == '/')
        {
            System.out.println("Error: Can't divide by zero.");
        }
        else
        {
            double totalTime = 0;

            for (int i = 0; i < timesToCalculate; i++)
            {
                long startTime = System.nanoTime();
                ans = calculator.Calculate(value1, value2, operator);
                long endTime = System.nanoTime();

                double duration = (endTime - startTime) / 1_000_000_000.0; // Calculate duration
                totalTime += duration;
            }
            
            double averageTime = totalTime / timesToCalculate;
            System.out.print(value1 + " " + operator + " " + value2 + " = " + ans + "\n");
            System.out.println("\nPerformed operation " + timesToCalculate + " times. Average time: " + averageTime + " seconds (" + averageTime * 1000 + " milliseconds).");
        }

        // Just waits for the user to press a key before continuing
        scanner.nextLine();
        ClearConsole();
    }

    void ClearConsole() // Taken from ChatGPT. Apparently it runs a system command. In this case it's "cls", which is the same way you clear the console in C++
    {
        try 
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } 
        catch (IOException | InterruptedException ex) 
        {
            ex.printStackTrace();
        }
    }
}
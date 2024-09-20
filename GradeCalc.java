import java.util.Scanner;

// Jared Scott's Grade Calculator 

public class GradeCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Exam scores
        int midterm = getValidScore(scanner, "Enter Midterm Exam score (out of 100): ", 100);
        int finalExam = getValidScore(scanner, "Enter Final Exam score (out of 100): ", 100);
        
        // Homework scores
        int hw1 = getValidHomeworkScore(scanner, "Enter Homework 1 score (out of 100): ");
        int hw2 = getValidHomeworkScore(scanner, "Enter Homework 2 score (out of 100): ");
        int hw3 = getValidHomeworkScore(scanner, "Enter Homework 3 score (out of 100): ");
        
        // Quiz scores
        int[] quizzes = new int[6];
        for (int i = 0; i < quizzes.length; i++) {
            quizzes[i] = getValidScore(scanner, "Enter score for Quiz " + (i + 1) + " (out of 10): ", 10);
        }
        
        // Drops the lowest quiz score
        int totalQuizScore = dropLowestQuiz(quizzes);
        
        // Calculates the total score
        double totalScore = calculateTotalScore(midterm, finalExam, hw1, hw2, hw3, totalQuizScore);
        
        // Displays results
        System.out.println("Overall the course score is: " + totalScore);
        System.out.println("Your minimum guaranteed letter grade is: " + getLetterGrade(totalScore));
        
        scanner.close();
    }

    // A method to get valid input score
    public static int getValidScore(Scanner scanner, String prompt, int maxScore) {
        int score;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                score = scanner.nextInt();
                if (score >= 0 && score <= maxScore) {
                    break;
                } else {
                    System.out.println("Inncorrect please enter a number between 0 and " + maxScore + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
        return score;
    }

    // A method to get valid homework score with late submission adjustment
    public static int getValidHomeworkScore(Scanner scanner, String prompt) {
        int score = getValidScore(scanner, prompt, 100);
        int submissionType;
        do {
            System.out.print("Was the homework submitted on time (1), late (2), or after late period (3)? ");
            submissionType = scanner.nextInt();
        } while (submissionType < 1 || submissionType > 3);
        
        // Applys penalties for late submission
        if (submissionType == 2) {
            score -= 10;
        } else if (submissionType == 3) {
            score = 0; 
        }
        
        return Math.max(score, 0); // Ensures score doesn't go negative
    }

    // A method to drop the lowest quiz score and sum the remaining
    public static int dropLowestQuiz(int[] quizzes) {
        int minQuiz = quizzes[0];
        int total = 0;
        for (int quiz : quizzes) {
            total += quiz;
            if (quiz < minQuiz) {
                minQuiz = quiz;
            }
        }
        total -= minQuiz; // Drops the lowest quiz score
        return total;
    }

    // A method to calculate total score
    public static double calculateTotalScore(int midterm, int finalExam, int hw1, int hw2, int hw3, int totalQuizScore) {
        double midtermWeight = 0.25, finalExamWeight = 0.35;
        double homeworkWeight = 0.30, quizWeight = 0.10;
        
        double homeworkTotal = (hw1 + hw2 + hw3) / 3.0; // The Average of homework scores
        return (midterm * midtermWeight) + (finalExam * finalExamWeight) + (homeworkTotal * homeworkWeight) + (totalQuizScore * quizWeight);
    }

    // A method to get minimum guaranteed letter grade
    public static String getLetterGrade(double totalScore) {
        if (totalScore >= 90) {
            return "A";
        } else if (totalScore >= 80) {
            return "B";
        } else if (totalScore >= 70) {
            return "C";
        } else if (totalScore >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}

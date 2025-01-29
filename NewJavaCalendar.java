/**********************
 * Lab Number: 3      *
 * Ashwani Panicker   *
 * Section Number: 01 *
 **********************/

 import java.util.Scanner;

public class NewJavaCalendar {

    public static void main(String[] args) {
        Scanner yr = new Scanner(System.in);
        System.out.print("Please enter the year: ");
        int year = yr.nextInt();
        int month = 1;
        String monthString = "";
        int days = 0;
        int dayOfWeek = calculateDayOfWeek(year);  // Calculate the first day of the year
        
        while (month <= 12) {
            // Set the number of days for each month
            switch (month) {
                case 1: monthString = "January"; 
                    days = 31;
                    break;
                case 2: monthString = "February";
                    if (isLeapYear(year)) {
                        days = 29;
                    } else {
                        days = 28; 
                    }    
                    break;
                case 3: monthString = "March"; 
                    days = 31;
                    break;
                case 4: monthString = "April";
                    days = 30;
                    break;
                case 5: monthString = "May";
                    days = 31;
                    break;
                case 6: monthString = "June";
                    days = 30;
                    break;
                case 7: monthString = "July";
                    days = 31;
                    break;
                case 8: monthString = "August";
                    days = 31;
                    break;
                case 9: monthString = "September";
                    days = 30;
                    break;
                case 10: monthString = "October";
                    days = 31;
                    break;
                case 11: monthString = "November";
                    days = 30;
                    break;
                case 12: monthString = "December";
                    days = 31;
                    break;                
            }

            // Print out the month header
            System.out.println(); 
            System.out.printf("%" + (11 + monthString.length() / 2) + "s\n", monthString);
            System.out.println();
            System.out.println("  S  M Tu  W Th  F  S");

            // Print spaces to align the first day of the month correctly
            if (dayOfWeek > 0) {
                System.out.printf("%" + (dayOfWeek * 3) + "c", ' ');
            }

            // Print the days of the month
            for (int d = 1; d <= days; ++d) {
                System.out.printf("%3d", d);
                dayOfWeek++;
                if (dayOfWeek == 7) {
                    dayOfWeek = 0;
                    System.out.println();
                }
            }

            System.out.println();
            month++;
        }
    }

    // To calculate Leap Year for the month of February
    public static boolean isLeapYear(int year) {
        if (year % 400 == 0 || (year % 4 == 0 && (year % 100 != 0))) {
            return true;
        }
        return false;    
    }

    // Method to calculate the first day of the year (Zeller's Congruence)
    public static int calculateDayOfWeek(int year) {
        int m = 1;  // January
        int d = 1;  // First day of the year
        int y = year;

        // Adjust the year and month for January and February (Zeller's algorithm)
        if (m == 1 || m == 2) {
            m += 12;
            y--;
        }

        // Zeller's Congruence formula
        int k = y % 100; // The year within the century
        int j = y / 100; // The century
        int dayOfWeek = (d + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 + 5 * j) % 7;

        // Zeller's returns 0=Saturday, 1=Sunday, ..., 6=Friday.
        // So adjust it so Sunday=0, Monday=1, ..., Saturday=6
        return (dayOfWeek + 6) % 7; // Now Sunday=0, Monday=1, ..., Saturday=6
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.StudentDao;
import dao.VaccineDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author asus
 */
public class validation {

    private static Scanner sc = new Scanner(System.in);

    public static int getAnInteger(String inPutMsg, String errorMsg, int lowerBound, int upperBound) {
        int n;
        while (true) {
            try {
                System.out.println(inPutMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < lowerBound || n > upperBound) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static String getStringByPattern(String inputMsg, String errorMsg) {
        String stdString;

        while (true) {
            System.out.println(inputMsg);
            stdString = sc.nextLine().trim();
            if (stdString.length() == 0 || stdString.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return stdString;
            }
        }
    }

    public static LocalDate getLDate(String inputMsg, String errMsg, String format) {
        while (true) {
            try {
                System.out.println(inputMsg);
                LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern(format));
                return date;

            } catch (Exception e) {
                System.out.println(errMsg);
            }
        }
    }

    public static LocalDate getLDate2(String inputMsg, String errMsg, String format, LocalDate date1, String err, String ignore) {
        while (true) {
            try {
                System.out.println(inputMsg);
                String input = sc.nextLine().trim();
                if (input != null && input.trim().equalsIgnoreCase(ignore)) {
                    return null;
                }
                LocalDate date2 = LocalDate.parse(input, DateTimeFormatter.ofPattern(format));
                if (date2.isAfter(date1.plusWeeks(12)) || date2.isBefore(date1.plusWeeks(4))) {
                    System.out.println(err);
                } else {
                    return date2;
                }

            } catch (Exception e) {
                System.out.println(errMsg);
            }
        }
    }

    public static String validyn(String inputMsg, String errMsg) {
        String answer = new String();
        while (true) {
            try {
                System.out.println(inputMsg);
                answer = sc.nextLine();
                if (!(answer.equalsIgnoreCase("yes")
                        || answer.equalsIgnoreCase("no"))) {
                    throw new Exception();
                }
                return answer;
            } catch (Exception e) {
                System.out.println(errMsg);
            }
        }
    }

    public static String getStudentID(String inputMsg, String errorMsg, StudentDao s, String err) {
        String student;
        while (true) {
            try {
                System.out.print(inputMsg);
                student = sc.nextLine().trim().toUpperCase();
                if (s.search(student).getNum() >= 1) {
                    System.out.println(err);
                } else {
                    return student;
                }
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }

    }

    public static String getSearchID(String inputMsg, String errMsg, StudentDao s) {
        String student;
        while (true) {
            System.out.println(inputMsg);
            student = sc.nextLine().trim();
            if (!s.isIDExist(student)) {
                System.out.println(errMsg);
            } else {
                return student;
            }
        }
    }

    public static String getVaccineID(String inputMsg, String err, VaccineDao v) {
        String vaccine;
        while (true) {
            try {
                System.out.print(inputMsg);
                vaccine = sc.nextLine().trim();
                if (!v.isIDExist(vaccine)) {
                    System.out.println(err);
                } else {
                    return vaccine;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static String getSearchName(String inputMsg, String errMsg, StudentDao student) {
        String data;
        while (true) {
            System.out.println(inputMsg);
            data = sc.nextLine().trim();
            if (!student.isSearchName(data)) {
                System.out.println(errMsg);
                return data;
            } else {
                return data;
            }
        }
    }
}

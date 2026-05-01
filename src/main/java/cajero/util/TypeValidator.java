package cesde.util;

import java.util.Scanner;

public class TypeValidator {

    static Scanner sc = new Scanner(System.in);

    public static int validateInt(String promt) {

        while (true) {
            try {
                System.out.println(promt);
                int value = sc.nextInt();
                sc.nextLine();
                return value;

            } catch (Exception e) {
                System.out.println("Ingrese un numero entero");
                sc.nextLine();
            }
        }
    }


    public static double validateDouble(String promt) {

        while (true) {
            try {
                System.out.println(promt);
                double value = sc.nextDouble();
                sc.nextLine();
                return value;

            } catch (Exception e) {
                System.out.println("Ingrese un numero decimal");
                sc.nextLine();
            }
        }
    }


    public static boolean validateBoolean(String promt) {

        while (true) {
            try {
                System.out.println(promt);
                boolean value = sc.nextBoolean();
                sc.nextLine();
                return value;

            } catch (Exception e) {
                System.out.println("Ingrese un valor logico");
                sc.nextLine();
            }
        }
    }


    public static String validateString(String promt) {

        while (true) {

            System.out.println(promt);
            String value = sc.nextLine().trim();
            if(!value.isEmpty()){
                return value;
            }
            System.out.println("El campo no puede estar vacio");
        }
    }










}


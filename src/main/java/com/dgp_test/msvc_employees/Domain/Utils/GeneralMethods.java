package com.dgp_test.msvc_employees.Domain.Utils;

public class GeneralMethods {
    public static String GetGeneralMessage(int option){
        return switch (option)
        {
            case 1 -> "Successfully created resource.";
            case 2 -> "Successfully Obtained Resources.";
            case 3 -> "Resource obtained correctly.";
            case 4 -> "Resource updated successfully.";
            case 5 -> "Resource deleted successfully.";
            case 6 -> "Resource Not Found.";
            case 7 -> "An internal server error has occurred.";
            case 8 -> "Database error.";
            case 9 -> "Validation error.";
            case 10 -> "Resource already exists.";
            case 11 -> "Partially created resource.";
            case 12 -> "Resources not inserted.";
            default -> "Invalid Option.";

        };
    }

}

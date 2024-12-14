package com.dinoology.hms.user.constants;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 7:43 PM
 */
public class UserResponseMessageConstants {
    //http
    //user
    public static final String USERNAME_FOUND = "Username Already Exits!";
    public static final String USER_ADDED_SUCCESSFULLY = "User Added Successfully!";
    public static final String ACCOUNT_DEACTIVATED_SUCCESSFULLY = "Account Deactivated Successfully!";
    public static final String ACCOUNT_NOT_FOUND = "Account Not Found!";
    public static final String STAFF_MEMBER_FOR_USER_NOT_FOUND = "Staff Member Not Found!";
    public static final String ACCOUNT_IN_DEACTIVATED = "Account already in Deactivated status!";
    public static final String ACCOUNT_IN_ACTIVATED = "Account already in Activated status!";

    public static final String UNABLE_TO_APPLY_CHANGES = "Unable to apply changes! Please try again later.";

    //user-type
    public static final String USER_TYPE_MUST_BETWEEN = "Type must be between 1 and 50 characters!";
    public static final String USER_TYPE_ADDED_SUCCESSFULLY = "User Type Added Successfully!";
    public static final String USER_TYPE_EXISTS = "User Type Already Exists!";
}

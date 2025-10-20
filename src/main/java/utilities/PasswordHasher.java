package utilities;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class PasswordHasher {
    // Define the BCrypt workload to use when generating password hashes.
    // 10-31 is a valid value.
    private static final int WORKLOAD = 12;

    /**
     * Hashes a supplied plaintext password using BCrypt
     * This automatically handles secure 128-bit salt generation and storage within the hash.
     * @param plaintext The account's password in plaintext form.
     * @return A string of length 60 containing the bcrypt hashed password in crypt(3) format - $id$salt$hash.
     * @throws IllegalArgumentException where supplied string is null or empty.
     */
    public static String hashPassword(String plaintext) {
        if(plaintext == null || plaintext.isEmpty()){
            throw new IllegalArgumentException("Cannot hash a null or empty string");
        }

        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plaintext, salt);
    }

    /**
     * Verifies a supplied plaintext password against a supplied hash value
     * @param plaintext The password to verify
     * @param hashedPassword The previously generated hash of the password
     * @return boolean - true if the password matches the hash, false otherwise (including if
     * either value is null)
     */
    public static boolean verifyPassword(String plaintext, String hashedPassword) {
        if(plaintext == null || hashedPassword == null || hashedPassword.isEmpty()){
            return false;
        }

        return BCrypt.checkpw(plaintext, hashedPassword);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String password = "Password01!";
        String hashed = PasswordHasher.hashPassword(password);

        System.out.println("Password: " + password);
        System.out.println("Computed hash: " + hashed);

        System.out.println("Enter password: ");
        String enteredPassword = input.nextLine();
        if(PasswordHasher.verifyPassword(enteredPassword, hashed)){
            System.out.println("Valid password!");
        }else{
            System.out.println("Incorrect password entered.");
        }
    }
}
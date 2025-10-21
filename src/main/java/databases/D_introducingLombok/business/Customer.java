package databases.D_introducingLombok.business;

import lombok.*;

import java.util.Objects;

/**
 *
 * @author michelle
 */

// Add getter methods
@Getter
// Add a toString method
@ToString
// Add equals and hashcode methods - only include the specifically noted variables
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Add the ability to build object with any components in any order
@Builder
// Add an all-args constructor
@AllArgsConstructor
public class Customer implements Comparable<Customer> {
    // Annotate all fields that cannot be null with NonNull
    // Don't include any auto-generating primary key fields as these may not be known when the object is created
    @EqualsAndHashCode.Include
    private int customerNumber;
    @NonNull
    private String customerName;
    @NonNull
    private String contactLastName;
    @NonNull
    private String contactFirstName;
    @NonNull
    private String phone;
    @NonNull
    private String addressLine1;
    private String addressLine2;
    @NonNull
    private String city;
    private String state;
    private String postalCode;
    @NonNull
    private String country;
    private int salesRepEmployeeNumber;
    private double creditLimit;

    public String format() {
        String formattedText = customerNumber + ": " + customerName
                + "\n\t" + contactLastName + ", " + contactFirstName
                + "\n\tPhone: " + phone
                + "\n\t\t" + addressLine1
                + "\n\t\t" + addressLine2
                + "\n\t\t" + city
                + "\n\t\t" + state
                + "\n\t\t" + postalCode
                + "\n\t\t" + country;
        if (salesRepEmployeeNumber != 0) {
            formattedText = formattedText + "\n\tSales Rep: " + salesRepEmployeeNumber;
        } else {
            formattedText = formattedText + "\n\tSales Rep: None";
        }
        formattedText = formattedText + "\n\tCredit limit: " + creditLimit;

        return formattedText;
    }

    public static boolean deepEquals(Customer c1, Customer c2){
        return Objects.equals(c1.customerNumber, c2.customerNumber)
                && Objects.equals(c1.customerName, c2.customerName)
                && Objects.equals(c1.contactFirstName, c2.contactFirstName)
                && Objects.equals(c1.contactLastName, c2.contactLastName)
                && Objects.equals(c1.phone, c2.phone)
                && Objects.equals(c1.addressLine1, c2.addressLine1)
                && Objects.equals(c1.addressLine2, c2.addressLine2)
                && Objects.equals(c1.city, c2.city)
                && Objects.equals(c1.state, c2.state)
                && Objects.equals(c1.postalCode, c2.postalCode)
                && Objects.equals(c1.country, c2.country)
                && Objects.equals(c1.salesRepEmployeeNumber, c2.salesRepEmployeeNumber)
                && Objects.equals(c1.creditLimit, c2.creditLimit);
    }

    @Override
    public int compareTo(Customer c) {
        if (customerNumber < c.customerNumber) {
            return -1;
        } else if (customerNumber > c.customerNumber) {
            return 1;
        }
        return 0;
    }
}

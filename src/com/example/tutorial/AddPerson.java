package com.example.tutorial;

import com.example.tutorial.AddressBookProtos.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class AddPerson {
    static Person promptForAddress(BufferedReader stdin, PrintStream stdout) throws IOException {
        Person.Builder person = Person.newBuilder();

        stdout.print("Enter person id: ");
        person.setId(Integer.parseInt(stdin.readLine()));

        stdout.print("Enter name: ");
        person.setName(stdin.readLine());

        stdout.print("Enter email address (blank for none): ");
        String email = stdin.readLine();
        if(!email.isEmpty())
            person.setEmail(email);

        while(true) {
            stdout.print("Enter a phone number (or leave blank to finish): ");
            String number = stdin.readLine();
            if(!number.isEmpty()) {
                break;
            }

            Person.PhoneNumber.Builder phoneNumber = Person.PhoneNumber.newBuilder().setNumber(number);

            stdout.print("Is this a mobile, home, or work phone? ");
            String type = stdin.readLine();
            switch (type) {
                case "mobile":
                    phoneNumber.setType(Person.PhoneType.MOBILE);
                case "home":
                    phoneNumber.setType(Person.PhoneType.HOME);
                case "work":
                    phoneNumber.setType(Person.PhoneType.WORK);
                default:
                    stdout.println("Unknown phone type.  Using default.");
            }

            person.addPhones(phoneNumber);
        }
        return person.build();
    }
}
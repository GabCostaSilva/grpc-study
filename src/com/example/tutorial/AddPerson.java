package com.example.tutorial;

import com.example.tutorial.AddressBookProtos.Person;
import com.example.tutorial.AddressBookProtos.AddressBook;

import java.io.*;

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

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage:  AddPerson ADDRESS_BOOK_FILE");
      System.exit(-1);
    }

    AddressBook.Builder addressBook = AddressBook.newBuilder();

    try {
      addressBook.mergeFrom(new FileInputStream(args[0]));
    } catch (FileNotFoundException e) {
      System.out.println(args[0] + ": File not found.  Creating a new file.");
    }

    addressBook.addPeople(promptForAddress(new BufferedReader(new InputStreamReader(System.in)), System.out));

    OutputStream output = new BufferedOutputStream(new FileOutputStream(args[0]));
    addressBook.build().writeTo(output);
    output.close();
  }
}

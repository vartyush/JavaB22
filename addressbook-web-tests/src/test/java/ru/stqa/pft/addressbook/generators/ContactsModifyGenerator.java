package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactsModifyGenerator {
    @Parameter(names = "-c", description = "Group count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactsModifyGenerator generator = new ContactsModifyGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);

        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContactsMod(count);
        if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognised format");
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }



    private List<ContactData> generateContactsMod(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withFirstname(String.format("VictoriaMod %s", i))
                    .withLastname(String.format("RubanovaMod %s", i))
                    .withEmail1(String.format("viktoriaMod.rub@t-systems.com %s", i))
                    .withEmail2(String.format("viktoriaMod.rub@t-systems.com %s", i))
                    .withEmail3(String.format("viktoriaMod.rub@t-systems.com %s", i))
                    .withHome(String.format("54565567Mod %s", i))
                    .withMobile(String.format("123456778Mod %s", i))
                    .withWork(String.format("123456778Mod %s", i))
                    .withGroup(String.format("testMod %s", i)));


        }
        return contacts;
    }

}


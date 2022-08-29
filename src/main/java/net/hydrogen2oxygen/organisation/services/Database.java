package net.hydrogen2oxygen.organisation.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.hydrogen2oxygen.organisation.domain.Organisation;

import java.io.File;
import java.io.IOException;

public class Database {

    private static Database instance;
    private ObjectMapper mapper;
    private final static String ORGANISATION_JSON = "data/organisation.json";

    private Database() {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Database instance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Organisation saveOrganisation(Organisation organisation) throws IOException {

        return saveOrganisation(organisation, "");
    }

    public Organisation saveOrganisation(Organisation organisation, String backupAppendum) throws IOException {

        new File("data").mkdirs();
        mapper.writeValue(new File(ORGANISATION_JSON + backupAppendum), organisation);
        return organisation;
    }

    public Organisation loadOrganisation() throws IOException {
        return mapper.readValue(new File(ORGANISATION_JSON), Organisation.class);
    }
}

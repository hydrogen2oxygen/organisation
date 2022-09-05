package net.hydrogen2oxygen.organisation.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.*;
import net.hydrogen2oxygen.organisation.domain.*;
import net.hydrogen2oxygen.organisation.exceptions.ErrorResponse;
import net.hydrogen2oxygen.organisation.services.Database;
import net.hydrogen2oxygen.organisation.services.Importer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static net.hydrogen2oxygen.organisation.services.Database.instance;

public class OrganisationController {

    private final static Log log = LogFactory.getLog(OrganisationController.class);
    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @OpenApi(
            summary = "Create or update Organisation",
            operationId = "saveOrUpdateOrganisation",
            path = "/organisation",
            method = HttpMethod.POST,
            tags = {"Organisation"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = Organisation.class)}),
            responses = {
                    @OpenApiResponse(status = "200"),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void saveOrUpdateOrganisation(Context ctx) throws IOException {
        ctx.status(200);
        Organisation org = ctx.bodyStreamAsClass(Organisation.class);
        org = Database.instance().saveOrganisation(org);
        ctx.json(org);
    }

    @OpenApi(
            summary = "Load Organisation",
            operationId = "loadOrganisation",
            path = "/organisation",
            method = HttpMethod.GET,
            tags = {"Organisation"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Organisation.class)}),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void loadOrganisation(Context ctx) throws IOException {
        try {
            Organisation org = instance().loadOrganisation();
            ctx.json(org);
        } catch (IOException e) {
            ctx.json(new Organisation());
        }
    }

    public static void importOrganisationHeader(Context ctx) {
        ctx.uploadedFiles("upload").forEach(file -> {
            ImportHeaderInfo importHeaderInfo = Importer.getInstance().importFileAndHeader(file.getFilename(), file.getContent());
            ctx.json(importHeaderInfo);
        });
    }

    public static void importOrganisationData(Context ctx) throws IOException {
        Importer.getInstance().importOrganisationData(ctx.bodyStreamAsClass(KeyValueMap.class));
    }

    public static void importGeoLocations(Context context) throws IOException, NoSuchAlgorithmException {

        File geoLocationsHashDirectory = new File("data/geoHash");
        if (!geoLocationsHashDirectory.exists()) {
            geoLocationsHashDirectory.mkdirs();
        }

        Organisation org = Database.instance().loadOrganisation();

        for (Group group : org.getGroups()) {
            for (Person person : group.getPersons()) {
                enrichAddressPosition(person);
            }
        }

        for (Person person : org.getPersonList()) {
            enrichAddressPosition(person);
        }

        Database.instance().saveOrganisation(org);
    }

    private static boolean enrichAddressPosition(Person person) throws NoSuchAlgorithmException, IOException {
        if (person.getAddress().getPosition() == null) {

            File geoHashAddressFile = new File("data/geoHash/" + getMD5fromAddress(person.getAddress()) + ".json");
            if (geoHashAddressFile.exists()) {
                Address geoHashAddress = mapper.readValue(geoHashAddressFile, Address.class);
                person.getAddress().setPosition(geoHashAddress.getPosition());
            } else {
                person.getAddress().setPosition(getNominativGeoLocation(person.getAddress()));
                saveAddressWithPositionHashFile(person.getAddress());
            }
            return true;
        }
        return false;
    }

    private static String saveAddressWithPositionHashFile(Address address) throws NoSuchAlgorithmException, IOException {

        String md5 = getMD5fromAddress(address);
        mapper.writeValue(new File("data/geoHash/" + md5 + ".json"), address);

        return md5;
    }

    @NotNull
    private static String getMD5fromAddress(Address address) throws NoSuchAlgorithmException {
        StringBuilder add = new StringBuilder();
        add
                .append(address.getStreet())
                .append(address.getHouseNumber())
                .append(address.getPostalCode())
                .append(address.getCity());

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(add.toString().getBytes());
        byte[] digest = md.digest();
        String md5 = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return md5;
    }

    private static Position getNominativGeoLocation(Address address) throws UnsupportedEncodingException {
        Position position = new Position();

        String url = "https://nominatim.openstreetmap.org/search?q=";
        String params = "";

        if (StringUtils.isNotEmpty(address.getStreet())) {
            params += address.getStreet();

            if (StringUtils.isNotEmpty(address.getHouseNumber())) {
                params += " " + address.getHouseNumber();
            }
        }

        if (StringUtils.isNotEmpty(address.getPostalCode())) {
            params += " " + address.getPostalCode();
        }

        if (StringUtils.isNotEmpty(address.getCity())) {
            params += " " + address.getCity();
        }

        if (StringUtils.isNotEmpty(address.getCountryIsoCode3())) {
            params += " " + address.getCountryIsoCode3();
        }

        url += URLEncoder.encode(params, "UTF-8") + "&format=json";

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            NominativJson[] nominativJson = mapper.readValue(entity1.getContent(), NominativJson[].class);
            if (nominativJson != null && nominativJson.length > 0) {
                position.setX(nominativJson[0].getLat().toString());
                position.setY(nominativJson[0].getLon().toString());
            }
            response1.close();
            httpclient.close();
        } catch (Exception e) {
            log.error(e);
            return null;
        }

        return position;
    }
}

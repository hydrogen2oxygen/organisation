package net.hydrogen2oxygen.organisation.controller;

import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.*;
import net.hydrogen2oxygen.organisation.domain.ImportHeaderInfo;
import net.hydrogen2oxygen.organisation.domain.KeyValueMap;
import net.hydrogen2oxygen.organisation.domain.Organisation;
import net.hydrogen2oxygen.organisation.exceptions.ErrorResponse;
import net.hydrogen2oxygen.organisation.services.Database;
import net.hydrogen2oxygen.organisation.services.Importer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import static net.hydrogen2oxygen.organisation.services.Database.instance;

public class OrganisationController {

    private final static Log log = LogFactory.getLog(OrganisationController.class);

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
}

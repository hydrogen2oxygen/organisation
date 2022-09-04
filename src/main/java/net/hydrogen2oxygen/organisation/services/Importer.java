package net.hydrogen2oxygen.organisation.services;

import net.hydrogen2oxygen.organisation.domain.ImportHeaderInfo;
import net.hydrogen2oxygen.organisation.domain.KeyValueMap;
import net.hydrogen2oxygen.organisation.domain.Organisation;
import net.hydrogen2oxygen.organisation.domain.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Importer {

    private final static Log log = LogFactory.getLog(Importer.class);
    private Workbook workbook = null;
    private static Importer instance = null;

    private Importer() {
    }

    public static Importer getInstance() {
        if (instance == null) {
            instance = new Importer();
        }
        return instance;
    }

    public ImportHeaderInfo importFileAndHeader(String fileName, InputStream fileInputStream) {
        log.info("Importing [" + fileName + "] ...");
        ImportHeaderInfo importHeaderInfo = new ImportHeaderInfo();
        try {
            File uploadDirectory = new File("upload");
            if (uploadDirectory.exists()) {
                for (File f : uploadDirectory.listFiles()) {
                    if (f.isFile()) {
                        try {
                            f.delete();
                        } catch (Exception e) {
                            log.error(e);
                        }
                    }
                }
            } else {
                uploadDirectory.mkdirs();
            }

            workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                row.forEach(cell -> {
                    if (StringUtils.isNotEmpty(cell.getStringCellValue())) {
                        importHeaderInfo.getHeaderNames().add(cell.getStringCellValue());
                    }
                });
                fileInputStream.close();
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return importHeaderInfo;
    }

    public void importOrganisationData(KeyValueMap keyValueMap) throws IOException {

        Sheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        final Map<String, Integer> mapColumnIds = new HashMap<>();
        final Map<String, String> mapKeyValue = new HashMap<>();
        final AtomicInteger column = new AtomicInteger(0);

        keyValueMap.getKeyValueList().forEach(keyValue -> {
            mapKeyValue.put(keyValue.getKey(), keyValue.getValue().replace("\"","").replace("\"",""));
        });

        for (Row row : sheet) {
            row.forEach(cell -> {
                mapColumnIds.put(mapKeyValue.get(cell.getStringCellValue()), column.intValue());
                column.getAndIncrement();
            });
            break;
        }

        boolean header = true;

        Organisation org = Database.instance().loadOrganisation();

        for (Row row : sheet) {
            if (header) {
                header = false;
                continue;
            }

            try {
                Person person = new Person();
                person.setLastName(row.getCell(mapColumnIds.get("LastName")).getStringCellValue());
                person.setFirstName(row.getCell(mapColumnIds.get("FirstName")).getStringCellValue());
                org.getPersonList().add(person);
            } catch (Exception e) {
                log.error(e);
            }
        }

        Database.instance().saveOrganisation(org);
    }
}

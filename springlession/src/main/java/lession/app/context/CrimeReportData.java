package lession.app.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i311352 on 7/9/16.
 */
@Component
public class CrimeReportData {
    private List<CrimeReportDataRow> crimeReportDataRows = new ArrayList<>();

    private FileProcessorService fileProcessorService;

    private static int count = 1;

    @Value("${report.fileName}")
    private String fileName;

//    @Autowired
//    private Environment environment;

    @Autowired
    @Qualifier("crimeReportDataResultDsc")
    private CrimeReportDataResult crimeReportDataResult;

    @Override
    public String toString() {
        return "CrimeReportData{" +
                "crimeReportDataRows=" + crimeReportDataRows +
                '}';
    }

    public CrimeReportData() {
        System.out.println("going to process filename " + fileName + " count is " + count++);
        //fileProcessorService.processFile(fileName);
    }


    public void load() {
        //String fileName = environment.getProperty("report.fileName");
        System.out.println("going to process filename " + fileName);
        System.out.println("Data result " + crimeReportDataResult.getSorting());
        fileProcessorService.processFile(fileName);
    }

    public List<CrimeReportDataRow> getCrimeReportDataRows() {
        return crimeReportDataRows;
    }

    public void setCrimeReportDataRows(List<CrimeReportDataRow> crimeReportDataRows) {
        this.crimeReportDataRows = crimeReportDataRows;
    }

    @Autowired
    public void setFileProcessorService(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }
}

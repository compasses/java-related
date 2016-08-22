package lession.app.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created by I311352 on 8/12/2016.
 */

public class CrimeReportDataResult {
    private String sorting;
    CrimeReportDataResult(String abc) {
        this.sorting = abc;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }
}

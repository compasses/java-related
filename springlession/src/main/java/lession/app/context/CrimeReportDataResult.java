package elastic.context;

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

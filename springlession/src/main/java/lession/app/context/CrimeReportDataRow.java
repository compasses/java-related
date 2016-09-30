package lession.app.context;

import org.springframework.stereotype.Component;

/**
 * Created by i311352 on 7/9/16.
 */
@Component
public class CrimeReportDataRow {
    private String crimeDescription;
    private Integer crimeAge;
    private Integer crimeRate;
    private Integer crimeYearlyRate;


    @Override
    public String toString() {
        return "CrimeReportDataRow{" +
                "crimeDescription='" + crimeDescription + '\'' +
                ", crimeAge=" + crimeAge +
                ", crimeRate=" + crimeRate +
                ", crimeYearlyRate=" + crimeYearlyRate +
                '}';
    }

    public String getCrimeDescription() {
        return crimeDescription;
    }

    public void setCrimeDescription(String crimeDescription) {
        this.crimeDescription = crimeDescription;
    }

    public Integer getCrimeAge() {
        return crimeAge;
    }

    public void setCrimeAge(Integer crimeAge) {
        this.crimeAge = crimeAge;
    }

    public Integer getCrimeRate() {
        return crimeRate;
    }

    public void setCrimeRate(Integer crimeRate) {
        this.crimeRate = crimeRate;
    }

    public Integer getCrimeYearlyRate() {
        return crimeYearlyRate;
    }

    public void setCrimeYearlyRate(Integer crimeYearlyRate) {
        this.crimeYearlyRate = crimeYearlyRate;
    }
}

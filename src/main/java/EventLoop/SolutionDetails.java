package EventLoop;

public class SolutionDetails {

    private final String departureStation;
    private final String arrivalStation;
    private final String AR;
    private final String departureDate;
    private final String departureTime;
    private final String adultsNumber;
    private final String childrenNumber;
    private final String onlyFrecce;
    private final String onlyRegionals;

    public SolutionDetails(String departureStation, String arrivalStation, String AR, String departureDate, String departureTime, String adultsNumber, String childrenNumber, String onlyFrecce, String onlyRegionals) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.AR = AR;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.adultsNumber = adultsNumber;
        this.childrenNumber = childrenNumber;
        this.onlyFrecce = onlyFrecce;
        this.onlyRegionals = onlyRegionals;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getAR() {
        return AR;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getAdultsNumber() {
        return adultsNumber;
    }

    public String getChildrenNumber() {
        return this.childrenNumber;
    }

    public String onlyFrecce() {
        return onlyFrecce;
    }

    public String onlyRegionals() {
        return onlyRegionals;
    }
}

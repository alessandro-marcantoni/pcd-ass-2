package EventLoop;

public class Details {

    private final int size;
    private String departureStation;
    private String lastStation;
    private String arrivalStation;

    private String programmedDeparture;
    private String effectiveDeparture;
    private String programmedLast;
    private String effectiveLast;
    private String programmedArrival;
    private String effectiveArrival;

    private String information;

    public static class Builder {

        private final int size;
        private String departureStation;
        private String lastStation;
        private String arrivalStation;

        private String programmedDeparture;
        private String effectiveDeparture;
        private String programmedLast;
        private String effectiveLast;
        private String programmedArrival;
        private String effectiveArrival;

        private String information;

        public Builder(final int size) {
            this.size = size;
        }

        public Builder departureStation(String s) {
            this.departureStation = s;
            return this;
        }

        public Builder lastStation(String s) {
            this.lastStation = s;
            return this;
        }

        public Builder arrivalStation(String s) {
            this.arrivalStation = s;
            return this;
        }

        public Builder programmedLast(String s) {
            this.programmedLast = s;
            return this;
        }

        public Builder effectiveLast(String s) {
            this.effectiveLast = s;
            return this;
        }

        public Builder programmedArrival(String s) {
            this.programmedArrival = s;
            return this;
        }

        public Builder effectiveArrival(String s) {
            this.effectiveArrival = s;
            return this;
        }

        public Builder programmedDeparture(String s) {
            this.programmedDeparture = s;
            return this;
        }

        public Builder effectiveDeparture(String s) {
            this.effectiveDeparture = s;
            return this;
        }

        public Builder information(String s) {
            this.information = s;
            return this;
        }

        public Details build() {
            return new Details(this);
        }

    }

    private Details(Builder builder) {
        this.size = builder.size;
        this.departureStation = builder.departureStation;
        this.lastStation = builder.lastStation;
        this.arrivalStation = builder.arrivalStation;
        this.programmedDeparture = builder.programmedDeparture;
        this.effectiveDeparture = builder.effectiveDeparture;
        this.programmedLast = builder.programmedLast;
        this.effectiveLast = builder.effectiveLast;
        this.programmedArrival = builder.programmedArrival;
        this.effectiveArrival = builder.effectiveArrival;
        this.information = builder.information;
    }

    public int getSize() {
        return size;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getLastStation() {
        return lastStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getProgrammedDeparture() {
        return programmedDeparture;
    }

    public String getEffectiveDeparture() {
        return effectiveDeparture;
    }

    public String getProgrammedLast() {
        return programmedLast;
    }

    public String getEffectiveLast() {
        return effectiveLast;
    }

    public String getProgrammedArrival() {
        return programmedArrival;
    }

    public String getEffectiveArrival() {
        return effectiveArrival;
    }

    public String getInformation() {
        return information;
    }
}

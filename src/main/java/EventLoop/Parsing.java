package EventLoop;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Parsing {

    public static List<Solution> getSolutions(JsonArray array) {
        return array.stream()
                .map(Object::toString)
                .map(JsonObject::new)
                .map(e -> new Solution(
                        e.getString("origin"),
                        e.getString("destination"),
                        new Date(e.getLong("departuretime")),
                        new Date(e.getLong("arrivaltime")),
                        e.getString("duration"),
                        e.getJsonArray("trainlist").stream()
                                .map(Object::toString)
                                .map(JsonObject::new)
                                .map(t -> t.getString("trainidentifier"))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public static Details getDetails(String html) {
        Document doc = Jsoup.parse(html);
        Element body = doc.body();

        Elements stations = body.select("div[class=corpocentrale]");
        final boolean arrived = stations.size() < 3; // 2 stations means that the train is arrived or not departed yet

        Element info = body.select("div[class=evidenziato]").last().getElementsByTag("strong").get(0);

        return new Details.Builder(stations.size())
                .departureStation(stations.get(0).getElementsByTag("h2").text())
                .lastStation(!arrived ? stations.get(1).getElementsByTag("h2").text() : "")
                .arrivalStation(stations.last().getElementsByTag("h2").text())
                .programmedDeparture(stations.get(0).getElementsByTag("p").get(0).getElementsByTag("strong").text().trim())
                .effectiveDeparture(stations.get(0).getElementsByTag("p").get(1).getElementsByTag("strong").text().trim())
                .programmedLast(!arrived ? stations.get(1).getElementsByTag("p").get(0).getElementsByTag("strong").text().trim() : "")
                .effectiveLast(!arrived ? stations.get(1).getElementsByTag("p").get(1).getElementsByTag("strong").text().trim() : "")
                .programmedArrival(stations.last().getElementsByTag("p").get(0).getElementsByTag("strong").text().trim())
                .effectiveArrival(stations.last().getElementsByTag("p").get(1).getElementsByTag("strong").text().trim())
                .information(info.text().trim().replace("Ultimo", "\nUltimo"))
                .build();
    }

    public static List<Train> getTrains(JsonArray array) {
        return array.stream()
                .map(Object::toString)
                .map(JsonObject::new)
                .map(e -> new Train.Builder(e.getInteger("numeroTreno"), e.getString("categoriaDescrizione").trim())
                        .origin(e.getString("origine", ""))
                        .destination(e.getString("destinazione", ""))
                        .platformArrival(e.getString("binarioProgrammatoArrivoDescrizione", ""))
                        .platformDeparture(e.getString("binarioProgrammatoPartenzaDescrizione", ""))
                        .arrivalTime(new Date(e.getLong("orarioArrivo") != null ? e.getLong("orarioArrivo") : 0))
                        .departureTime(new Date(e.getLong("orarioPartenza") != null ? e.getLong("orarioPartenza") : 0))
                        .delay(e.getInteger("ritardo", 0))
                        .build())
                .collect(Collectors.toList());
    }

}

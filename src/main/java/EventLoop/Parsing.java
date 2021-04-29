package EventLoop;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static Map<String, String> getDetails(String html, String trainID) {
        final Map<String, String> detail = new HashMap<>();
        Document doc = Jsoup.parse(html);
        Element body = doc.body();

        // stations
        Elements stations = body.select("div[class=corpocentrale]");
        detail.put("size", String.valueOf(stations.size()));
        detail.put("dep_station", stations.get(0).getElementsByTag("h2").text());
        if(stations.size() > 2) detail.put("last_station", stations.get(1).getElementsByTag("h2").text()); // train not arrived yet
        detail.put("arr_station", stations.last().getElementsByTag("h2").text());

        // times
        detail.put("dep_prog", stations.get(0).getElementsByTag("p").get(0).getElementsByTag("strong").text().trim());
        detail.put("dep_eff", stations.get(0).getElementsByTag("p").get(1).getElementsByTag("strong").text().trim());
        if(stations.size() > 2) {
            detail.put("last_prog", stations.get(0).getElementsByTag("p").get(0).getElementsByTag("strong").text().trim());
            detail.put("last_eff", stations.get(0).getElementsByTag("p").get(1).getElementsByTag("strong").text().trim());
        }
        detail.put("arr_prog", stations.last().getElementsByTag("p").get(0).getElementsByTag("strong").text().trim());
        detail.put("arr_eff", stations.last().getElementsByTag("p").get(1).getElementsByTag("strong").text().trim());

        // delay
        Element info = body.select("div[class=evidenziato]").last().getElementsByTag("strong").get(0);
        detail.put("info", info.text().trim().replaceAll("<br>", "\n"));
        return detail;
    }
}

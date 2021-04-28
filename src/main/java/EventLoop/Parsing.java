package EventLoop;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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
}

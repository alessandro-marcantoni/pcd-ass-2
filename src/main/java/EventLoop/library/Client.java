package EventLoop.library;

import EventLoop.*;
import EventLoop.model.Details;
import EventLoop.model.Parameters;
import EventLoop.model.Solution;
import EventLoop.model.Train;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

import java.util.List;

import java.util.*;

public class Client extends AbstractVerticle {

    private final WebClient client;

    public Client(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    public Future<List<Solution>> getTrainSolutions(Parameters parameters) {
        Promise<List<Solution>> promise = Promise.promise();
        client
                .get(443, "www.lefrecce.it", "/msite/api/solutions?arflag=A&adultno=1&childno=0&direction=A&frecce=false&onlyRegional=false")
                .ssl(true)
                .addQueryParam("origin", parameters.getDepartureStation())
                .addQueryParam("destination", parameters.getArrivalStation())
                .addQueryParam("adate", parameters.getDepartureDate())
                .addQueryParam("atime", parameters.getDepartureTime())
                .send()
                .onSuccess(res -> {
                    if (res.statusCode() == 200) {
                        promise.complete(Parsing.getSolutions(res.bodyAsJsonArray()));
                    }
                })
                .onFailure(err -> promise.fail(err.getMessage()));
        return promise.future();
    }


    public Future<List<Train>> getRealTimeStationInfo(String stationID, String mode) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Promise<List<Train>> promise = Promise.promise();
        client
                .get(80, "www.viaggiatreno.it", "/viaggiatrenonew/resteasy/viaggiatreno/"+ mode + "/" + stationID + "/" + new Date().toString().replaceAll(" ", "%20"))
                .send()
                .onSuccess(res -> {
                    if (res.statusCode() == 200) {
                        promise.complete(Parsing.getTrains(res.bodyAsJsonArray()));
                    }
                })
                .onFailure(err -> promise.fail(err.getMessage()));
        return promise.future();
    }

    public Future<Details> getRealTimeTrainInfo(String trainID) {
        Promise<Details> promise = Promise.promise();

        client
                .get(443,"www.viaggiatreno.it", "/vt_pax_internet/mobile/scheda")
                .ssl(true)
                .addQueryParam("numeroTreno", trainID)
                .send()
                .onSuccess(res -> {
                    if (res.statusCode() == 200) {
                        promise.complete(Parsing.getDetails(res.bodyAsString()));
                    }
                })
                .onFailure(err -> promise.fail(err.getMessage()));
        return promise.future();
    }

}

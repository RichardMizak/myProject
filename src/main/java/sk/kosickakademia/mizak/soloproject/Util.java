package sk.kosickakademia.mizak.soloproject;

import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Util {
    public String getJSON(List<Game> list) {
        if (list.isEmpty())
            return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime", getTimeAndDate());
        object.put("size", list.size());
        JSONArray jsonArray = new JSONArray();
        for (Game u : list) {
            JSONObject userJson = new JSONObject();
            userJson.put("id", u.getId());
            userJson.put("game", u.getGame());
            userJson.put("genre", u.getGenre());
            jsonArray.add(userJson);
        }
        object.put("games", jsonArray);
        return object.toString();
    }

    public String getTimeAndDate() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return date.format(now);
    }

}

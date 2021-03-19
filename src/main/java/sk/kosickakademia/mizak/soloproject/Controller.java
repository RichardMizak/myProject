package sk.kosickakademia.mizak.soloproject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    Log log=new Log();
    @PostMapping("/games/add")
    public ResponseEntity<String> addGame(@RequestBody String input) {
        JSONObject o = null;
        try {
            o = (JSONObject) new JSONParser().parse(input);
            String name = (String.valueOf(o.get("name")));
            String genre = (String.valueOf(o.get("genre")));
            if (name==null || genre==null || name.trim().length()==0 || genre.trim().length()==0) {
                log.error("Missing Firstname or Lastname or incorrect age.");
                JSONObject object = new JSONObject();
                object.put("ERROR", "Missing Firstname or Lastname");
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(o.toJSONString());
            }
            Game game=new Game(name,genre);
        } catch (ParseException e) {
            log.error("ERROR");
            e.printStackTrace();
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(null);
    }
    @GetMapping("/users")
    public ResponseEntity<String> games(){
        List<Game> list=new Database().getAllGames();
        String json=new Game().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
}

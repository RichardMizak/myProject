package sk.kosickakademia.mizak.soloproject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    Database database = new Database();
    Log log=new Log();
    @PostMapping("/games/add")
    public ResponseEntity<String> addGame(@RequestBody String input) {
        JSONObject o = null;
        try {
            o = (JSONObject) new JSONParser().parse(input);
            String name = (String.valueOf(o.get("name")));
            String genre = (String.valueOf(o.get("genre")));
            if (name==null || genre==null || name.trim().length()==0 || genre.trim().length()==0) {
                log.error("Missing game or genre.");
                JSONObject object = new JSONObject();
                object.put("ERROR", "Missing game or genre");
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(o.toJSONString());
            }
            Game game=new Game(name,genre);
            new Database().insertNewGame(game);
        } catch (ParseException e) {
            log.error("ERROR");
            e.printStackTrace();
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(null);
    }
    @GetMapping("/games")
    public ResponseEntity<String> games(){
        List<Game> list=new Database().getAllGames();
        String json=new Game().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
    @PutMapping("/games/{id}")
    public ResponseEntity<String> changeGame(@PathVariable int id,@RequestBody String body) {
        JSONObject o=new JSONObject();
        try {
            o= (JSONObject) new JSONParser().parse(body);
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }catch(ParseException e) {
            e.printStackTrace();
        }
        String data=String.valueOf(o.get("newGame"));
        System.out.println("data:"+data);
        if(data.equalsIgnoreCase("null")){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        String newGame = String.valueOf(data);
        if(newGame==null){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        boolean result = new Database().changeGame(id,newGame);
        if(result){
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("");
        }else{
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body("");
        }
    }
    @RequestMapping(value="/game/{id}",method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteGame(@PathVariable int id){
        if (database.getGameById(id)==null){
            JSONObject object=new JSONObject();
            object.put("ERROR","Game not found");
            log.error("Game not found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
        }
        database.deleteGame(id);
        log.print("Game deleted");
        return ResponseEntity.status(204).contentType(MediaType.APPLICATION_JSON).body(null);
    }
}

package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;


public class ResultJson {

    private JSONObject resu;

    private ResultJson()
    {
        resu = new JSONObject();
        resu.put("bestiary",new JSONArray());
    }

    /** Instance unique pré-initialisée */
    private final static ResultJson INSTANCE = new ResultJson();

    /** Point d'accès pour l'instance unique du singleton */
    static ResultJson getInstance()
    {	return INSTANCE;
    }

    public void sendResults(String monster, ArrayList<String> spells){
        JSONObject obj = new JSONObject();
        obj.put("monster",monster);
        JSONArray be = (JSONArray) resu.get("bestiary");
        be.add(obj);
        if(!spells.isEmpty()){
            obj.put("spells",new JSONArray());
            JSONArray list = (JSONArray) obj.get("spells");
            for (String s: spells) {
                list.add(s);
            }
        }

    }

    public String toString(){
        return resu.toString();
    }

}

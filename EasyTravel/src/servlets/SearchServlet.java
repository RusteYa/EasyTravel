package servlets;

import entities.Place;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import repositories.PlaceRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Rustem.
 */
@WebServlet(name = "SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        String req = request.getParameter("req");
        String req_type = request.getParameter("req_type");
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        List<Place> places = PlaceRepository.getRepository().getPlace();
        for (Place place : places) {
            String header = place.getHeader();
            String content = place.getContent();
            switch (req_type) {
                case "header":
                    if (header.toLowerCase().contains(req.toLowerCase())) {
                        JSONObject o = new JSONObject();
                        o.put("photoPath", place.getPhotoPath());
                        o.put("header", place.getHeader());
                        o.put("content", place.getContent());
                        o.put("id", place.getId());
                        ja.put(o);
                    }
                    break;
                case "content":
                    if (content.toLowerCase().contains(req.toLowerCase())) {
                        JSONObject o = new JSONObject();
                        o.put("photoPath", place.getPhotoPath());
                        o.put("header", place.getHeader());
                        o.put("content", place.getContent());
                        o.put("id", place.getId());
                        ja.put(o);
                    }
                    break;
            }
        }
        try {
            jo.put("places", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().print(jo.toString());
        response.getWriter().close();
    }
}

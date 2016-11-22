package controller;

import com.google.gson.Gson;
import doa.locationInforDAO;
import model.locationInfor;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hamzaghani on 26/10/2016.
 */
@WebServlet(name = "locationDB")
public class locationDB extends HttpServlet {
    private static final Logger log = Logger.getLogger(locationDB.class);
    locationInforDAO infor;
    locationInfor location;
    String getData;
    String lat, lon;
     Gson gson = new Gson();

    public void init() throws ServletException {
        infor= new locationInforDAO();
        location= new locationInfor();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        getData = request.getParameter("getdata");

        if (getData==null){
            lat = request.getParameter("lat");
            lon = request.getParameter("lon");
            if (!(lat == null) && !(lon == null)) {
                location.setLat(Float.parseFloat(lat));
                location.setLon(Float.parseFloat(lon));
                //infor.add(sensorNameStr, sensorValueStr);

            }else{
                log.error("bad data has been sent");
            }

        }else {
            response.setContentType("application/json");
            String json = gson.toJson(location);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.close();
            System.out.println(json);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);


    }
}

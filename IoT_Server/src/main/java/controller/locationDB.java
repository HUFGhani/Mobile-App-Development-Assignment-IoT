package controller;

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
import java.util.ArrayList;

/**
 * Created by hamzaghani on 26/10/2016.
 */
@WebServlet(name = "locationDB")
public class locationDB extends HttpServlet {
    private static final Logger log = Logger.getLogger(locationDB.class);
    locationInforDAO infor;
    ArrayList<locationInfor> location;
    String getData;
    String lat, lon;

    public void init() throws ServletException {
        infor= new locationInforDAO();
        location= new ArrayList<locationInfor>();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        getData = request.getParameter("data");

        if (getData==null){
            lat = request.getParameter("lat");
            lon = request.getParameter("lon");
            if (!(lat == null) && !(lon == null)) {
                //infor.add(sensorNameStr, sensorValueStr);

            }else{
                log.error("bad data has been sent");
            }

        }else {

            /*response.setContentType("application/json");
            String json = "{\"location\": {\"" + lat +
                    "\": \"" + lon + "\"}}";*/
            PrintWriter out = response.getWriter();
            out.print(lat + " " +lon);
            out.close();
            System.out.println(lat + "  "  + lon);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);


    }
}

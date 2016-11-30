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
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by hamzaghani on 26/10/2016.
 */
@WebServlet(name = "locationDB")
public class locationDB extends HttpServlet {
    private static final Logger log = Logger.getLogger(locationDB.class);
    locationInforDAO infor;
    locationInfor location;
    String getData;
    String lat,lng,email;
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
            lng = request.getParameter("lng");
            email = request.getParameter("email");

            if (!(lat == null) && !(lng == null) && !(email == null)) {
                java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
                BigDecimal latBD  = new BigDecimal(lat);
                BigDecimal lngBD = new BigDecimal(lng);
                location.setEmail(email);
                location.setLat(latBD);
                System.out.println(latBD);
                location.setLng(lngBD);
                location.setTimestape(date);
                infor.add(location);

            }else{
                log.error("bad data has been sent");
            }

        }else {
            email = request.getParameter("email");
            if (!(email==null)){

                location.setEmail(email);
                ArrayList<locationInfor> locations =  infor.getdata(email);

            response.setContentType("application/json");


            String json = gson.toJson(locations);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.close();
            System.out.println(json);
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);


    }
}

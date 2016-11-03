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

    public void init() throws ServletException {
        infor= new locationInforDAO();
        location= new ArrayList<locationInfor>();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        getData = request.getParameter("data");
        if (getData==null){
            String lan = request.getParameter("lan");
            String log = request.getParameter("lat");
            if (!(lan == null) && !(log == null)) {
                infor.add( lan, log);
            }else{

            }

        }else {
            response.setContentType("application/json");

/*
        String json = "{\"sensor\": {\"" + lastValidSensorNameStr +
                "\": \"" + lastValidSensorValueStr + "\"}}";
*/

            PrintWriter out = response.getWriter();
            //System.out.println("DEBUG: json return: "+json);
            //out.print(json);
            out.close();

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

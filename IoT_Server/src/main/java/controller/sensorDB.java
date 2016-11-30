package controller;

import com.google.gson.Gson;
import doa.sensorInforDAO;
import model.sensorInfor;
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
 * Created by hamzaghani on 25/10/2016.
 */
@WebServlet(name = "sensorDB")
public class sensorDB extends HttpServlet {

    private static final Logger log = Logger.getLogger(sensorDB.class);

    sensorInforDAO infor;
    sensorInfor sensor;
    String getData;
    Gson gson = new Gson();
    private String sensorNameStr;
    private String sensorValueStr;
    private String email;

    public void init() throws ServletException {
        infor= new sensorInforDAO();
        sensor= new sensorInfor();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        getData = request.getParameter("getdata");

        if (getData==null){
            sensorNameStr = request.getParameter("sensorname");
            sensorValueStr = request.getParameter("sensorvalue");
            email = request.getParameter("email");

            if (!(sensorNameStr == null) && !(sensorValueStr == null) && !(email==null)) {
                java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
                boolean sensorValueboolean = Boolean.parseBoolean(sensorValueStr);
                sensor.setEmail(email);
                sensor.setSensorName(sensorNameStr);
                sensor.setSensorValue(sensorValueboolean);
                sensor.setTimeStamp(date);
                infor.add(sensor);

            }else{
                log.error("bad data has been sent");

            }
        }else {
            email = request.getParameter("email");
            if (!(email==null)) {
                ArrayList <sensorInfor> sensors = infor.getdata(email);
                response.setContentType("application/json");
                String json = gson.toJson(sensors);
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

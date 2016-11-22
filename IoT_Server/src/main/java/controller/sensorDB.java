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
    ArrayList<sensorInfor> sensor;
    String getData;
    Gson gson = new Gson();
    public void init() throws ServletException {
        infor= new sensorInforDAO();
        sensor= new ArrayList<sensorInfor>();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        getData = request.getParameter("getdata");

        if (getData==null){
            String sensorNameStr = request.getParameter("sensorname");
            String sensorValueStr = request.getParameter("sensorvalue");
            if (!(sensorNameStr == null) && !(sensorValueStr == null)) {
                //infor.add(sensorNameStr, sensorValueStr);
                System.out.println(sensorNameStr + "  "  + sensorValueStr);
            }else{
                log.error("bad data has been sent");
            }

        }else {
            response.setContentType("application/json");

            String json = gson.toJson(sensor);
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

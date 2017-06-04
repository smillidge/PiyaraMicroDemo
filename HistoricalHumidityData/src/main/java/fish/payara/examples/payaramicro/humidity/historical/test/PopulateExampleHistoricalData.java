/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.humidity.historical.test;

import fish.payara.cdi.jsr107.impl.NamedCache;
import fish.payara.examples.payaramicro.humidity.HumidityDataCollection;
import fish.payara.examples.payaramicro.humidity.HumidityMeasurement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.cache.Cache;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Steve Millidge <Payara Services Limited>
 */
@WebServlet(name = "PopulateExampleHistoricalData", urlPatterns = {"/PopulateExampleHistoricalData"})
public class PopulateExampleHistoricalData extends HttpServlet {
    
    @Inject
    @NamedCache(cacheName = "HumidityGrid")
    Cache cache;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PopulateExampleHistoricalData</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Populating 100 Device Historical Data</h1>");
            
            for (int i = 0; i < 100; i++) {
                String key = "Sensor"+ i;
                if (cache.get(key) == null) {
                    HumidityDataCollection measurements = new HumidityDataCollection();
                    Calendar cal = new GregorianCalendar();
                    for (int hour = 0; hour < 23; hour ++) {
                        for (int minute = 0; minute < 60; minute++) {
                            cal.add(Calendar.MINUTE, 1);
                            float measurement = (float) (Math.random()*10.0+40.0);
                            HumidityMeasurement hm = new HumidityMeasurement(measurement);
                            hm.setDate(cal.getTimeInMillis());
                            hm.setSensorName(key);
                            measurements.addMeasurement(hm);
                        }
                    }
                    cache.put(key, measurements);
                }
                
            }
            
            
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

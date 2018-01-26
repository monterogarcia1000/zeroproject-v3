package com.iesemilidarder.porjectozero.web;

import com.iesemilidarder.porjectozero.ConexionBaseDeDatos;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Hacemos que tanto por Post como por Get nos haga lo mismo

public class mostrarrestaurantServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//Recogemos y enviamos el parametro para la busqueda en la base de datos de la información del restaurante

        String idInformacio = request.getParameter("id");

        ConexionBaseDeDatos conexionBaseDeDatos = new ConexionBaseDeDatos();

        request.setAttribute("idInformacio", conexionBaseDeDatos.readRestaurant2(idInformacio));

        request.getRequestDispatcher("informacion.jsp").forward( request, response );
    }
}

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.iesemilidarder.porjectozero.*" %>
<%@page import="java.util.*" %>
<%@page import="java.lang.String" %>

<!doctype html>
<html lang="ca">
<head>
    <title>Restaurants</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<!-- Navigation bar -->
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="#">Restaurants</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <form>
            <div class="dropdown">
                <button type="button" class="btn dropdown-toggle" data-toggle="dropdown">
                    Tipus de restaurants
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="index.jsp">Chines</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="index.jsp">Italià</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="index.jsp">Japonés</a>
                </div>
            </div>
        </form>
    </div>

    <!-- Formulario para la busqueda de restaurantes -->

        <form action="index.jsp" class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" name="cercar" type="text" placeholder="Cercar" aria-label="Cercar">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Cercar</button>
        </form>

</nav>

<main role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-3">RESTAURANTS DARDER</h1>
            <p>Aquesta es una pagina on podras trobar els millors restaurants de les Illes Balears</p>
        </div>
    </div>

    <div class="container">
        <!-- Mostramos por pantalla los contenidos de la base de datos -->
        <%
            ConexionBaseDeDatos conexionBaseDeDatos = new ConexionBaseDeDatos();
            ArrayList rst = (ArrayList) conexionBaseDeDatos.readRestaurant(request.getParameter("cercar"));
            Iterator itr = rst.iterator();
            while (itr.hasNext()){
                Restaurant rstt = (Restaurant) itr.next();
                out.println("<div class='row'>");
                out.println("<div class='col-md-4'>" +
                        "<img class='img-fluid' src='"+ rstt.getUrl_imagen() +"'>" +
                        "</div>");
                out.println("<div class='col-md-8'><h2>" + rstt.getNombre() +
                        "</h2>" + "<p>" + rstt.getDireccion() + "</p>" +
                        "<p>" + rstt.getWeb() + "</p>" +
                        "<p>" + rstt.getTelefono() + "</p>" +
                        "<p>" + rstt.getTipo() + "</p>" +
                        //Le pasamos una id a monstrarrrestaurantServlet
                        "<p><a class='btn btn-secondary' role='button'>Més informació &raquo;</a></p>" +
                        "</div>");
                out.println("</div>");
            }
        %>
        <hr>

    </div> <!-- /container -->

</main>

<footer class="container">
    <p>&copy; Iván Montero García 2017</p>
</footer>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>

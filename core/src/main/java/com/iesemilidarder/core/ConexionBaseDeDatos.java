package com.iesemilidarder.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//Lectura de los restaurantes guardados en la base de datos

public class ConexionBaseDeDatos {

//Ni zorra de lo que estoy haciendo
    private static final String DRIVER ="oracle.jdbc.driver.OracleDriver";

    private static final String THIN_URL ="jdbc:oracle:thin:@35.205.41.45:1521:XE";

    private static final String USER ="usuari";

    private static final String PASSWORD ="usuari";

    private Object searchDB(String query, Function<ResultSet, Object> f){

        Connection con;

        Statement stmt;

        try {

          Class.forName(DRIVER);

          con = DriverManager.getConnection(THIN_URL,USER,PASSWORD);

          stmt = con.createStatement();

          ResultSet rs = stmt.executeQuery(query);

          Object o =f.apply(rs);

          stmt.close();

          con.close();

          return o;

      }catch (Exception z){

          return null;
      }

    }

    public List<Restaurant> readRestaurant(String cercar) {

        List<Restaurant> arrayRestaurants = new ArrayList<>();

        try {

//Version GENERICS
            String query = "SELECT * FROM (SELECT RE.RES_NOM, RE.RES_ADRECA, RE.RES_WEB, RE.RES_TELEFON, RE.RES_URL_IMG, RR.TRS_DESCRIPCIO, RE.RES_CODI FROM" +
                    "RESTAURANTS RE, TRESTAURANTS RR WHERE RE.RES_TRS_CODI = RR.TRS_CODI ORDER BY RES_MITJANA DESC)where ROWNUM <= 5";

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@35.205.41.45:1521:XE", "usuari", "usuari");

            Statement stmt = con.createStatement();

//Hacemos una query directamente a la base de datos
            ResultSet rs = stmt.executeQuery(query);

//Indicamos que si hay mas restaurantes siga imprimiendo
            ResultSetMapper<Restaurant> mapper = new ResultSetMapper<>();
            arrayRestaurants = mapper.mapRersultSetToObject(rs,Restaurant.class);

            stmt.close();

            con.close();

//Version FUNCTIONAL
            arrayRestaurants = new ArrayList<>();

            Function<ResultSet, Object> func = new Function<ResultSet, Object>() {

                public Object apply(ResultSet rs){

                    return mapper.mapRersultSetToObject(rs, Restaurant.class);

                }

            };

            arrayRestaurants = (ArrayList) searchDB(query, func);

            System.out.println("yuhu v2");

        }catch (Exception e) {

            System.out.println(e.toString());

        }

        return arrayRestaurants;

    }

/*
//Le pasamos al lector de base de datos el parametro que queremos utilizar para buscar en nuestro formulario
    public static ArrayList readRestaurant(String cercar) {

        ArrayList rst = new ArrayList();

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@35.205.41.45:1521:XE", "usuari", "usuari");

            Statement stmt = con.createStatement();
            ResultSet rs;

//Le decimos como queremos que actue cuando le llega el parametro vacio y cuando le llega con lo que queremos buscar

            if (cercar == null || cercar.equals("")) {

//Asi actua el programa cuando el parametro le llega vacio

                rs = stmt.executeQuery("SELECT * FROM (SELECT RE.RES_NOM, RE.RES_ADRECA, RE.RES_WEB, RE.RES_TELEFON, RE.RES_URL_IMG, RR.TRS_DESCRIPCIO, RE.RES_CODI FROM " +
                        "RESTAURANTS RE, TRESTAURANTS RR WHERE RE.RES_TRS_CODI = RR.TRS_CODI ORDER BY RES_MITJANA DESC)where ROWNUM <= 5");


            } else {

//Asi actua el programa cuando el parametro le llega con lo que queremos buscar

                rs = stmt.executeQuery("SELECT * FROM (SELECT RE.RES_NOM, RE.RES_ADRECA, RE.RES_WEB, RE.RES_TELEFON, RE.RES_URL_IMG, RR.TRS_DESCRIPCIO, RE.RES_CODI FROM " +
                        "RESTAURANTS RE, TRESTAURANTS RR WHERE RE.RES_TRS_CODI = RR.TRS_CODI AND RE.RES_NOM LIKE '%" + cercar + "%' ORDER BY RES_MITJANA DESC)where ROWNUM <= 5");

            }

//Guarda los datos que extrae de la base de datos

            while (rs.next()) {

                Restaurant rstt = new Restaurant();

                rstt.setNombre(rs.getString("RES_NOM"));
                rstt.setDireccion(rs.getString("RES_ADRECA"));
                rstt.setWeb(rs.getString("RES_WEB"));
                rstt.setTelefono(rs.getString("RES_TELEFON"));
                rstt.setTipo(rs.getString("TRS_DESCRIPCIO"));
                rstt.setUrl_imagen(rs.getString("RES_URL_IMG"));
                rstt.setID(rs.getString("RES_CODI"));

                rst.add(rstt);

            }

            stmt.close();
            con.close();

        } catch (Exception e) {

            System.out.println(e.toString());

        }

        return rst;

    }
*/

//Metodo que lee el Servlet para mostrarnos la informacion de un restaurante en concreto

    public Restaurant readRestaurant2(String idInformacio){

        Restaurant rstt = null;

        try{

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@35.205.41.45:1521:XE", "usuari", "usuari");

            Statement stmt = con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT RE.RES_NOM, RE.RES_ADRECA, RE.RES_WEB, RE.RES_TELEFON, RE.RES_URL_IMG, RR.TRS_DESCRIPCIO, RE.RES_CODI FROM RESTAURANTS RE, TRESTAURANTS RR WHERE "+ idInformacio +"=RE.RES_CODI AND RE.RES_TRS_CODI = RR.TRS_CODI");

            if (rs.next()) {

                rstt = new Restaurant();

                rstt.setNombre(rs.getString("RES_NOM"));
                rstt.setDireccion(rs.getString("RES_ADRECA"));
                rstt.setWeb(rs.getString("RES_WEB"));
                rstt.setTelefono(rs.getString("RES_TELEFON"));
                rstt.setTipo(rs.getString("TRS_DESCRIPCIO"));
                rstt.setUrl_imagen(rs.getString("RES_URL_IMG"));
                rstt.setID(rs.getString("RES_CODI"));

//Obtenemos los comentarios de la base de datos

                Statement stmtt = con.createStatement();
                ResultSet rsst;

                rsst = stmtt.executeQuery("SELECT OP.OPI_OBSERVACIO, US.USU_NOM FROM RESTAURANTS RS, OPINIONS OP, USUARIS US WHERE  RS.RES_CODI = OP.OPI_RES_CODI AND " + idInformacio +"= RS.RES_CODI AND OP.OPI_USU_CODI = US.USU_CODI");

                while (rsst.next()) {

                    rstt.getOpinions().add(rsst.getString("USU_NOM"));
                    rstt.getOpinions().add(rsst.getString("OPI_OBSERVACIO"));

                }

                stmtt.close();

            }

            stmt.close();
            con.close();

        }catch (Exception b){

            System.out.println(b.toString());

        }

        return rstt;
    }

}
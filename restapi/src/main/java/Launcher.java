import com.iesemilidarder.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;
import java.lang.reflect.Method;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.*;
import static spark.Spark.*;

/**
 * dummies
 * com.iesemilidarder.fordummies.web
 * Created by alber in 14/12/2017.
 * Description:
 * <p>
 * User[] a;
 * <p>
 * a(10)
 */

//Base de datos interna para aprender a usar la rest API

public class Launcher {
    private static List<User> lUser = new ArrayList<User>();
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    private static void init() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("a" + i);
            user.save();
            lUser.add(user);
        }
        DBObject dbo = lUser.get(0);
        log.info("Loading finished");
    }



    /**
     * Method to check html output or not
     *
     * @param request
     * @return
     */
    private static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return StringUtils.contains(accept, "text/html");
        //return accept != null && accept.contains("text/html");
    }

    private static void setResponseHeader(Response response, boolean isHtml) {
        response.status(200);
        response.type(isHtml ? "text/html" : "application/json");
    }

    private static FreeMarkerEngine getFreemarkerEngine() {
        //as https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
            cfg.setClassForTemplateLoading(Launcher.class, "/templates");
            FreeMarkerEngine freemarker = new FreeMarkerEngine(cfg);
            return freemarker;
        } catch (Exception e) {
            log.error("Loading Freemarker Engine, loading default configuration", e);
            return new FreeMarkerEngine();
        }
    }

//Empezamos a utilizar SPARK

    public static void main(String... args) {
        staticFiles.location("/public");
        init();
        port(8090);
        /*
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/public";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/public");
        }*/
        //hello world for dummies, via lambdas
        get("/hello", (req, res) -> "Hello World");
        //json response way1: via spark renderer
        /*get("/json", "application/json", (request, response) -> {
            User user = new User();
            user.setName("Hello world!");
            return user;
        }, new JsonTransformer());*/
        //biconditional response way2: via jackson
        get("/users", (request, response) -> {
            if (shouldReturnHtml(request)) {
                Map<String, Object> model = new HashMap<>();
                model.put("posts", lUser);
                model.put("title", "Users");
                model.put("subtitle", "List of all users");
                return getFreemarkerEngine().render(
                        new ModelAndView(model, "basicView.ftl")
                );
            } else {
                CorsFilter.apply();
                ObjectMapper mapper = new ObjectMapper();
                setResponseHeader(response, false);
                return mapper.writeValueAsString(lUser);
            }
        });

// Pequeña parte de una API rest que nos enseña los restaurantes de la base de datos

        get("/restaurant2", (request, response) -> {
            if (shouldReturnHtml(request)) {
                Map<String, Object> model = new HashMap<>();
                model.put("posts", ConexionBaseDeDatos.readRestaurant(""));
                model.put("title", "Restaurants");
                model.put("subtitle", "List of all restaurants");
                return getFreemarkerEngine().render(
                        new ModelAndView(model, "basicView.ftl")
                );
            } else {
                CorsFilter.apply();
                ObjectMapper mapper = new ObjectMapper();
                setResponseHeader(response, false);
                return mapper.writeValueAsString(ConexionBaseDeDatos.readRestaurant(""));
            }
        });

        /*get("/scrap",(request,response)->{
            Map<String, Object> model = new HashMap<>();
            String query = ".center-content";
            String url = "https://www.meneame.net/";
            ScrappingCommand cmd = new ScrappingCommand(url, query);
            model.put("title", "Scrapping");
            model.put("url", url);
            model.put("element", query);
            model.put("content", cmd.getResult());
            return getFreemarkerEngine().render(
                    new ModelAndView(model, "scrapView.ftl")
            );
        });*/
    }

}

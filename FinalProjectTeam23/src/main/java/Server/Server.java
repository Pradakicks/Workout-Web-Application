package Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/Server")
public class Server extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set CORS headers to allow requests from your React app
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");  // Optional, if you're handling cookies

        response.setStatus(HttpServletResponse.SC_OK);


    	@SuppressWarnings("unused")
        PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();

        HttpSession session = request.getSession(false);
        Integer userID = null;
        if (session != null) {
            userID = (Integer) session.getAttribute("user_id");
        }

        userID = 1;
        
        if (userID != null) {
            // Assuming getTrainer function is defined in your DBCConnection class
             List<Trainer> trainers = DBConnection.getTrainers();
             for(Trainer trainer : trainers){
                System.out.println(trainer.getTrainerId());
             }

            if (trainers != null) {
                // Convert the trainer object to JSON and send it as a response
                out.println(gson.toJson(trainers));
            } else {
                // If no trainer is found, send an empty JSON object
                out.println("{}");
            }
        }
    }
}

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
        
        if (userID != null) {
            // Assuming getTrainer function is defined in your DBCConnection class
             List<Trainer> trainers = DBConnection.getTrainers();

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

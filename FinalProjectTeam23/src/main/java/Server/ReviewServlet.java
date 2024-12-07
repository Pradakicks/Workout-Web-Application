package Server;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String trainerIdParam = request.getParameter("trainerId");
        String clientIdParam = request.getParameter("clientId");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (trainerIdParam != null) {
            // Fetch reviews for a specific trainer
            UUID trainerId = UUID.fromString(trainerIdParam);
            List<Review> reviews = DBConnection.fetchTrainerReviews(trainerId);
            out.println(gson.toJson(reviews));
        } else if (clientIdParam != null) {
            // Fetch reviews for a specific client
            UUID clientId = UUID.fromString(clientIdParam);
            List<Review> reviews = DBConnection.fetchUserReviews(clientId);
            out.println(gson.toJson(reviews));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Missing required parameters.\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Review review = gson.fromJson(reader, Review.class);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (review.validate()) {
            boolean success = DBConnection.addReview(review);
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("{\"message\": \"Review added successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"error\": \"Failed to add review.\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid review data.\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        UpdateReviewRequest updateRequest = gson.fromJson(reader, UpdateReviewRequest.class);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (updateRequest.validate()) {
            boolean success = DBConnection.editReview(
                updateRequest.getReviewId(),
                updateRequest.getNewRating(),
                updateRequest.getNewComment()
            );
            if (success) {
                out.println("{\"message\": \"Review updated successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"error\": \"Failed to update review.\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid update data.\"}");
        }
    }

    static class UpdateReviewRequest {
        private UUID reviewId;
        private int newRating;
        private String newComment;

        public UUID getReviewId() {
            return reviewId;
        }

        public int getNewRating() {
            return newRating;
        }

        public String getNewComment() {
            return newComment;
        }

        public boolean validate() {
            return reviewId != null && newRating >= 1 && newRating <= 5 && newComment != null && newComment.length() <= 50;
        }
    }
}

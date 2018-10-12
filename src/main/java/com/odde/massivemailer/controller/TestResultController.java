package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.QuestionResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/testresult")
public class TestResultController extends AppController{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String quizId=(String) request.getParameter("quizId");

        // Get Question Responses
        List<QuestionResponse>  questionResponseList = QuestionResponse.where(" test_id = ? ", quizId);
        int correctAnswers=0;
        int totalQuestions=0;
        for(QuestionResponse questionResponse : questionResponseList){
            correctAnswers += (boolean)questionResponse.get("is_answer_correct") ? 1: 0;
            totalQuestions++;
        }

        request.setAttribute("correctAnswers", correctAnswers);
        request.setAttribute("totalQuestions", totalQuestions);
        int percentage=(correctAnswers*100)/totalQuestions;
        request.setAttribute("percentage", percentage);
        request.setAttribute("message", getAdviceOnPercentage(percentage));

        RequestDispatcher dispatch = request.getRequestDispatcher("test_result.jsp");
        dispatch.forward(request, response);
    }

    private String getAdviceOnPercentage(int percentage){
        if(percentage >=90 ) {
            return "Excellent :D";
        }
        else if(percentage >=80){
            return "Good :)";
        }
        else if (percentage >= 60 ){
            return "Please, try to get better score!!!";
        }
        return "Too bad !!! :(";
    }
}


package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.QuestionOption;
import com.odde.massivemailer.model.onlinetest.Question;
import org.bson.types.ObjectId;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/onlinetest/add_question")
public class AddQuestionController extends AppController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/onlinetest/add_question.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMsg = checkParameter(req);
        if (!errorMsg.isEmpty()) {
            req.setAttribute("errorMessage", errorMsg);
            RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/add_question.jsp");
            dispatch.forward(req, resp);
            return;
        }
        saveQuestion(req);
        resp.sendRedirect("/dashboard");
    }

    private String checkParameter(HttpServletRequest req) {
        String check = req.getParameter("check");

        String paramName = getOptionParamName(req);
        List<EmptyValidator> validators = Arrays.asList(
                new EmptyValidator(req.getParameter("description")),
                new EmptyValidator(req.getParameter(paramName + "1")),
                new EmptyValidator(req.getParameter(paramName + "2")),
                new EmptyValidator(req.getParameter(paramName + check))
        );
        for (EmptyValidator validator : validators) {
            if (validator.inValid()) {
                return "Invalid inputs found!";
            }
        }

        if (check == null) {
            return "Right answer is not selected!";
        }

        return "";
    }

    private String getOptionParamName(HttpServletRequest req) {
        String type = req.getParameter("type");
        String paramName;
        if ("single".equals(type)) {
            paramName = "option";
        } else {
            paramName = "checkbox";
        }
        return paramName;
    }

    private void saveQuestion(HttpServletRequest req) {
        String type = req.getParameter("type");

        Question question = new Question(req.getParameter("description"), req.getParameter("advice"), new ObjectId(req.getParameter("category")), type.equals("multiple"), false);
        question.save();

        final String[] checks = req.getParameterValues("check");
        List<String> checksList = Arrays.asList(checks);


        for (int i = 0; i < 6; i++) {
            String optionDescription;
            if ("single".equals(type)) {
                optionDescription = req.getParameter("option" + (i + 1));
            } else {
                optionDescription = req.getParameter("checkbox" + (i + 1));
            }
            if (!optionDescription.isEmpty()) {
                boolean isCorrect = checksList.contains(String.valueOf(i + 1));
                QuestionOption questionOption = new QuestionOption(optionDescription, isCorrect, question.getId());
                questionOption.save();
            }
        }
    }

    private class EmptyValidator {
        private String value;

        public EmptyValidator(String value) {
            this.value = value;
        }

        public boolean inValid() {
            return "".equals(this.value);
        }
    }
}

package com.odde.massivemailer.controller.tokkun;

import com.odde.massivemailer.controller.AppController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TokkunController extends AppController {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("selectedOption", new ArrayList(Arrays.asList("a")));
    }
}

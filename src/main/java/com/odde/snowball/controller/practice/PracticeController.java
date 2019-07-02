package com.odde.snowball.controller.practice;


import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.practice.Practice;
import org.bson.types.ObjectId;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@WebServlet("/practice/practice")
public class PracticeController extends AppController {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    public Practice ensurePractice(ObjectId userId) {
        Practice practice = Practice.fetchPracticeByUserId(userId);
        if (practice == null){
            Collection<ObjectId> categories = new ArrayList<>();
            List<Integer> cycle = Arrays.asList(1, 3, 7);
            practice = new Practice(userId, 1, categories, cycle);
            practice.save();
        }
        return practice;
    }
}

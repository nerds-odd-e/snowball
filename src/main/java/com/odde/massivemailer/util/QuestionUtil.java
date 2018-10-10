package com.odde.massivemailer.util;

import com.odde.massivemailer.model.AnswerOption;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;

public class QuestionUtil {

    public static String getCorrectOptionId(Collection<AnswerOption> options){
        Optional<AnswerOption> correctId = options.stream().filter(option -> option.isCorrect()).findFirst();
        return  correctId.isPresent()? correctId.get().getId().toString(): StringUtils.EMPTY;
    }
}

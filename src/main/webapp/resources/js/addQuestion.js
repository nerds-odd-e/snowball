function switchOptionView(choiceType)
{
    if (choiceType == 1) {
        $("#checkboxes").css("display", "none");
        $("#options").css("display", "block");
    } else {
        $("#checkboxes").css("display", "block");
        $("#options").css("display", "none");
    }
}
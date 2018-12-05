$(document).ready(function() {
	switchOptionView("single");
});

function switchOptionView(choiceType)
{
    if (choiceType === "single") {
        $("#options").css("display", "block");
        $("#checkboxes").css("display", "none");
    } else {
        $("#options").css("display", "none");
        $("#checkboxes").css("display", "block");
    }
}
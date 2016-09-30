function onRollBtnClick(rolltype) {
    $.ajax({
        method: "POST",
        url: "emersonsgame",
        data: {
            "roll": rolltype
        },
        success: onSuccessCallback,
    });
}

var onSuccessCallback = function (result) {
    var gameState = JSON.parse(result);
    if(gameState.error) {
        alert(gameState.error)
        return;
    }
    updateGameState(gameState);
}

function updateGameState(gameState) {
    $("#dice").text(gameState.dieResult);
    $("#scars").text(gameState.playerScar);
    $("#currentDistance").text(gameState.playerPos);
    $("#distance").text(gameState.distance);
}


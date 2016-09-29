$(document).ready(function() {
    $("#normalRollButton").on("click", normalRollBtnClicked);
    $("#superRollButton").on("click", superRollBtnClicked);
});

function normalRollBtnClicked(e) {
    var results = mockRollCall(1,0,1, 20);
    updateGameState(results);
}

function superRollBtnClicked(e) {
    var results = mockRollCall(5,1,5, 20);
    updateGameState(results);
}

function updateGameState(gameState) {
    $("#dice").text(gameState.dieResult);
    $("#scars").text(gameState.playerScar);
    $("#currentDistance").text(gameState.playerPos);
    $('#distance').text(gameState.distance);
}

function mockRollCall(dice, scars, playerDistance, distance) {
    return {
        dieResult: dice,
        playerScar: scars,
        playerPos: playerDistance,
        distance: distance
    }
}


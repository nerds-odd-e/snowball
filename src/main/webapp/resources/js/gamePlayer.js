$(document).ready(function() {
    $("#normalRollButton").on("click", normalRollBtnClicked);
    $("#superRollButton").on("click", superRollBtnClicked);
})

function normalRollBtnClicked(e) {
    var results = mockRollCall(1,1);
    updateGameState(results.dice, results.playerDistance);
}

function superRollBtnClicked(e) {
    var results = mockRollCall(5,5);
    updateGameState(results.dice, results.playerDistance);
}

function updateGameState(dice, playerDistance) {
    $("#dice").html(dice);
    $("#currentDistance").html(playerDistance);
}

function mockRollCall(dice, playerDistance) {
    return {
        dice: dice,
        playerDistance: playerDistance
    }
}

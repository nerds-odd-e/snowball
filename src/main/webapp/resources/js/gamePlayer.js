$(document).ready(function() {
    $("#normalRollButton").on("click", normalRollBtnClicked);
    $("#superRollButton").on("click", superRollBtnClicked);
})

function normalRollBtnClicked(e) {
    var results = mockRollCall(1,0,1);
    updateGameState(results.dice, results.scars,results.playerDistance);
}

function superRollBtnClicked(e) {
    var results = mockRollCall(5,1,5);
    updateGameState(results.dice, results.scars,results.playerDistance);
}

function updateGameState(dice, scars, playerDistance) {
    $("#dice").html(dice);
    $("#scars").html(scars);
    $("#currentDistance").html(playerDistance);
}

function mockRollCall(dice, scars, playerDistance) {
    return {
        dice: dice,
        scars: scars,
        playerDistance: playerDistance
    }
}

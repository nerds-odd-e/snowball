

var toPoll = true;
var player = {
    id: "aPlayer",
    dist: 3,
    scars: 0
};

$(document).ready(function(){
    var interval = setInterval(function() { waitForPlayers(); }, 1000);

    $("#startSpecBtn").on("click", function() {
        checkGameState();
        $("#startSpecBtn").text("Next");
        $("#startSpecBtn").unbind("click");

        $("#startSpecBtn").on("click", function() { checkGameState(); });
        clearInterval(interval);
    });
})

function updateAndDrawPlayer(playerList) {
    var html = '';
    for (var p in playerList) {
        html += updatePlayerPosition(playerList[p]);
    }
    $('#canvas').html(html);
}

function waitForPlayers() {
    // single player 
    var playerInfo = getPlayerInfo();
    var playerList = JSON.parse(playerInfo);
    updatePlayerCount(playerList.length);
    updateAndDrawPlayer(playerList);
}

function checkGameState(){
    // make API call -> check game state
    //if game ended -> set toPoll = false
    /*(var results = this.mockMultiplePlayersAjaxCall();

    if(results.status == "ENDED"){
        this.toPoll = false;
        return;
    }
    emptyCanvas();
    updateScreenWithPlayerData(results);
    */

    // single player 
    var playerInfo = getPlayerInfo();
    var playerList = JSON.parse(playerInfo);
    updateAndDrawPlayer(playerList);
}

function updatePlayerPosition(player){
    var html = '';

    if(player != undefined || player != null) {
        html = '<div class="race-track">'+
                    '<div class="racer" style="margin-left:'+player.position*10+'px">' +
                        '<div id="racerName">ID: '    + player.ID+
                    '</div>'+
                    '<div id="racerEmail">Email: '+ player.email     + '</div>'+
                    '<div id="racerDist">Dist: ' + player.position + '</div>'+
                    '<div id="scar">Scar: '      + player.scars     + '</div>' +
                '</div>'+
            '</div>';
    }

    return html;
}

function getPlayerInfo() {
    var r = "";
    $.ajax({
        type:           "GET",
        async:          false,
        contentType:    "application/json",
        url:            "/massive_mailer/emersonsgame/Players",
        success: function(data) {
            r = data;
        },
        error: function(err) {
            console.log(err);
        }
    });
    return r;
}

function updateScreenWithPlayerData(results){
    for (var index in results.players) {
        this.updatePlayerPosition(results.players[index]);
        this.updatePlayerCount();
    }
}

function emptyCanvas(){
    $('#canvas').empty();
}

function updatePlayerCount(totalPlayers){
    $('#player-count').html(totalPlayers);
}


var toPoll = true;
var player = {
    id: "aPlayer",
    dist: 3,
    scars: 0
};

function sendGameDistance() {
  $.post('/massive_mailer/emersonsgame', {distance: $("#inputDistance").val()}, function(){}, 'json');
}

$(document).ready(function(){
    sendGameDistance();
    var interval = setInterval(function() { checkGameState(); }, 1000);

    $("#startSpecBtn").on("click", function() {
        $("#startSpecBtn").text("Next");

        if (interval){
            clearInterval(interval);
        }

        proceedToNextRound();
        checkGameState();
    });
})

function proceedToNextRound() {
    $.ajax({
        type:           "POST",
        async:          false,
        contentType:    "application/json",
        url:            "/massive_mailer/emersonsgame/nextround",
        success: function(data) {
        },
        error: function(err) {
            console.log(err);
        }
    });
}

function updateCanvas(playerList) {
    var html = '';
    for (var p in playerList) {
        html += createPlayerDiv(playerList[p]);
    }
    $('#canvas').html(html);
}


function checkGameState(){
    var playersInfo = getPlayersInfo();
    var playersList = JSON.parse(playersInfo);
    updatePlayerCount(playersList.length);
    updateCanvas(playersList);
}

function createPlayerDiv(player){
    var indentation = player.position * 10;
    var scar = "scar" + (player.scars < 7 ? player.scars : 6) ;
    return '<div class="race-track">'+
                '<div class="racer" style="margin-left:' + indentation +   'px">' +
                    '<div id="racerScar" class="racerCar ' + scar + '" style="display: inline-block;"></div>'+
                    '<div id="racerEmail" style="display: inline-block; margin-left: 8px;">Email: ' + player.email      + '</div>'+
                '</div>'+
            '</div>';
}

function getPlayersInfo() {
    var r = "[]";

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

function emptyCanvas(){
    $('#canvas').empty();
}

function updatePlayerCount(totalPlayers){
    $('#player-count').html(totalPlayers);
}


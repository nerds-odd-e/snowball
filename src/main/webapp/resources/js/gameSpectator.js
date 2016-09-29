
var totalDist = 20;
var toPoll = true;
var player = {
    id: "aPlayer",
    dist: 3,
    scars: 0
};
var movDis = 0;

$(document).ready(function(){
    var interval = setInterval(function() {
            checkGameState();
        if(!this.toPoll){
            clearInterval(interval);
        }
    }, 3000);
})

function checkGameState(){
    // make API call -> check game state
    //if game ended -> set toPoll = false

    var results = this.mockMultiplePlayersAjaxCall();

    if(results.status == "ENDED"){
        this.toPoll = false;
        return;
    }
    emptyCanvas();
    updateScreenWithPlayerData(results);

}

function updatePlayerPosition(player){
    if(player != undefined || player != null){
        $('#canvas').append(
            '<div class="racer">' +
                '<div id="racerName">' + player.id + '</div>'+
                '<div id="racerDist">Dist: ' + player.dist + '</div>'+
                '<div id="scar">Scar: ' + player.scars + '</div>' +
            '</div>'
        );
    }
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

function updatePlayerCount(){
//    console.log('called', $('#player-count'));
    $('#player-count').html('1');
}

function mockAjaxCall(){
    return {
        status: "PLAYING",
        player: {
            id: "bPlayer",
            dist: movDis++,
            scars: 1
        },
        distance: 20
    };

}

function mockMultiplePlayersAjaxCall(){
    return {
        status: "PLAYING",
        players: [
            {
                id: 'id',
                dist: 0,
                scars: 0
            },
            {  id: 'id2',
               dist: 0,
               scars: 0
            }

        ]
    }
}
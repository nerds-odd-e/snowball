
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
    var results = this.mockAjaxCall();
    if(results.status == "ENDED"){
        this.toPoll = false;
    }
    else{
        $('#inputDistance').val(results.distance);
        this.updatePlayerPosition(results.player);
        this.updatePlayerCount();
    }
}

function updatePlayerPosition(player){
    if(player != undefined || player != null){
        $('#canvas').empty();
        $('#canvas').append(
            '<div class="racer">' +
                '<div id="racerName">' + player.id + '</div>'+
                '<div id="racerDist"> Dist: ' + player.dist + '</div>'+
                '<div id="scar"> Scar: ' + player.scars + '</div>' +
            '</div>'
        );
    }
}

function updatePlayerCount(){
    console.log('called', $('#player-count'));
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
describe('gameSpectator',function(){

   var rootId = "canvas";

    beforeEach(function(){
        var container = document.createElement('div');
        container.setAttribute('id', rootId);
        document.body.appendChild(container);
    });

    describe('Single player', function() {
        it('should show 1 racer on screen when server responds with 1 player',function() {
            var racerData = {   status: "PLAYING",
                                players: [{
                                    id: "bPlayer",
                                    dist: 0,
                                    scars: 0
                                }],
                                distance: 20
                            };
            updateScreenWithPlayerData(racerData);
            console.log(document.getElementById("canvas"));
            expect(document.getElementById("racerName").innerHTML).toBe("bPlayer");
            expect(document.getElementById("racerDist").innerHTML).toBe("Dist: 0");
            expect(document.getElementById("scar").innerHTML).toBe("Scar: 0");
        });
    });

});

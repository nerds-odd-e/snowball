describe('gameSpectator',function(){

   var rootId = "canvas";

    beforeEach(function(){
        var container = document.createElement('div');
        container.setAttribute('id', rootId);
        document.body.appendChild(container);
    });

    afterEach(function(){
        emptyCanvas();
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
            expect(document.getElementById("racerName").innerHTML).toBe("bPlayer");
            expect(document.getElementById("racerDist").innerHTML).toBe("Dist: 0");
            expect(document.getElementById("scar").innerHTML).toBe("Scar: 0");
        });
    });


    describe('Multi player', function (){
        it('should show correct details for 2 players when server responds', function(){
            var racerData = {
                status: "PLAYING",
                players:[
                {
                    id: 'player1',
                    dist: 0,
                    scars: 0
                },
                {
                    id: 'player2',
                    dist: 1,
                    scars: 1
                }],
                distance: 20
            };

            updateScreenWithPlayerData(racerData);

            $('.racer').each(function(key, value){
                var racerName = $(this).find('#racerName').text();
                var racerDist = $(this).find('#racerDist').text();
                var racerScars = $(this).find('#scar').text();

                expect(racerName).toBe(racerData.players[key].id);
                expect(racerDist).toBe("Dist: " + racerData.players[key].dist);
                expect(racerScars).toBe("Scar: " + racerData.players[key].scars);
            });

        });
    });

});



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GAME</title>

<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/lib/bootstrap/css/game.css" rel="stylesheet">
<link href="resources/lib/bootstrap/css/plugins/morris.css" rel="stylesheet">
<style>
    .racer {
        height: 68px;
    }
    .racerCar {
        width: 170px;
        height: 68px;
    }
    .scar0 { background-image: url("resources/images/car-scar0.png"); }
    .scar1 { background-image: url("resources/images/car-scar1.png"); }
    .scar2 { background-image: url("resources/images/car-scar2.png"); }
    .scar3 { background-image: url("resources/images/car-scar3.png"); }
    .scar4 { background-image: url("resources/images/car-scar4.png"); }
    .scar5 { background-image: url("resources/images/car-scar5.png"); }
    .scar6 { background-image: url("resources/images/car-scar6.png"); }
</style>

</head>

<body>

<div>
    <div id="canvas">

    </div>
    <div class="footer">
        <form class="form-inline">
            <div class="form-group">
                <label for="inputDistance">Distance</label>
                <input type="text" class="form-control"  id="inputDistance" placeholder="Distance" value=<%= request.getParameter("distance")%> disabled>
            </div>
            <div class="form-group">
                <label for="inputType">Game Type</label>
                <input type="text" class="form-control" id="inputType" value="Roll Last" disabled>
            </div>
            <div class="form-group">
                <div id="startSpecBtn" class="btn btn-default">Start</div>
            </div>
        </form>
    </div>
    <div class="player-badge">
        <div class="player-badge-counter">
            <span class="player-count" id="player-count">0</span>
            <span>Players joined</span>
        </div>
    </div>
</body>

<!-- jQuery -->
<script type="text/javascript" src="resources/lib/bootstrap/js/jquery.js"></script>
<script type="text/javascript" src="resources/lib/jquery-template/jquery.loadTemplate-1.4.4.js"></script>

<script type="text/javascript" src="resources/js/gameSpectator.js"></script>
<script>

</script>
</html>
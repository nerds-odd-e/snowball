Feature: QR Code
  As the player I want to be able to scan the qr_code
  so that I can play the game.
  
  Scenario: QR Code should direct to correct path
    Given Open QR Code Scanner
    When Scanner checks "https://raw.githubusercontent.com/nerds-odd-e/massive_mailer/master/src/main/webapp/resources/images/qrcode.png"
    Then Scanner should return "http://localhost:8070/massive_mailer/game_login.jsp"

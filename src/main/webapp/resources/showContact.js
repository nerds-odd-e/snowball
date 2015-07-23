/**
 * 
 */

$.getJSON( "showContact", function( json ) {
  console.log( "JSON Data: " + json[0].id );
  var $contactTable = $('#contactTable')
  $.each(json, function(idx, item){
      $contactTable.append("<tr><td>" + item.name + "</td></tr>")
  })
 });


/**
 * 
 */

$.getJSON( "showContact", function( json ) {
  console.log( "JSON Data: " + json[0].id );
  var $ul = $('#contactTable')
  $.each(data, function(idx, item){
      $ul.append('<li style="color: ' + item.color + '">' + item.name + '-' + item['class'] +'</li>')
  })
 });


$(document).ready(function() {
$("#batchFile").change(function() {
		var batchContent = document.getElementById("batchFile").files[0];
               var fileNameLength = batchContent.name.split('.').length;
               var fileFormat = batchContent.name.split('.')[fileNameLength-1];
               if (fileFormat==='csv') {
                    var reader = new FileReader();
                    reader.readAsText(batchContent,'ISO-8559-1');
                    reader.onload = function(e) {
                          var text = reader.result;
                          groupCsvData = [text];
                          callback(reader.result);
                    };
               }
               else {
                    alert('Only CSV file is Allowed');
                    document.getElementById("batchFile").value="";
               }
 })
 })

function callback(value){
       //document.getElementById("contacts").innerHTML = value;
       document.getElementById("data").value = value;
       var rows = value.replace('\n',';');
       document.forms[0].submit();
       alert("Batch Contacts Uploaded");
}
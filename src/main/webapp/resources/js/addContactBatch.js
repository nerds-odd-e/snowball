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
                  submitUploadData(reader.result);
            };
         }
        else {
           alert('Only CSV file is Allowed');
           document.getElementById("batchFile").value="";
        }
    })
 })

function submitUploadData(value){
       //document.getElementById("contacts").innerHTML = value;
       var rows = value.replace('\n',';');
       document.getElementById("data").value = rows;
       document.forms[0].submit();
       alert("Batch Contacts Uploaded");
}
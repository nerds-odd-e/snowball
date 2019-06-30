$(document).ready(function() {
$("#batchFile").change(function() {
		var batchContent = document.getElementById("batchFile").files[0];
        var reader = new FileReader();
        reader.readAsText(batchContent,'ISO-8559-1');
        reader.onload = function(e) {
             var csvValue = reader.result;
             formatCsvData(csvValue);
             submitUploadData();
        };
    })
 })

function submitUploadData() {
    document.forms[0].submit();
    alert("Batch Contacts Uploaded");
}

exports.formatCsvData = function formatCsvData(value) {
    var rows = value.replace(/\n/g,';');
    document.getElementById("data").value = rows;
}
var addContactBatchFunctions = require("../../../main/webapp/resources/js/addContactBatch.js");
var { wait } = require("@testing-library/dom");

describe('file upload function', function() {
     it('should format csv data', async function() {
        var markup = "<form><input id='data'/></form>";

        var container = document.createElement('div');
     	document.body.appendChild(container);
     	container.innerHTML = markup;

        var value = "firstName,lastName\n"+"test,test";
        addContactBatchFunctions.formatCsvData(value);
        await wait(function() {
          expect(document.getElementById("data").value).toEqualCaseInsensitive("firstName,lastName;test,test");
        });
     });
});
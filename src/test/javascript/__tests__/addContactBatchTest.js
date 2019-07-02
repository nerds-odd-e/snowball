var { wait } = require("@testing-library/dom");
var addContactBatchFunctions = require("../../../main/webapp/resources/js/addContactBatch.js");

describe('file upload function', function() {
     it('should format csv data', async function() {
        var markup = "<form><input id='data'/></form>";

        var container = document.createElement('div');
     	document.body.appendChild(container);
     	container.innerHTML = markup;

        var value = "firstName,lastName\n"+"test,test";
        addContactBatchFunctions.formatCsvDataExp(value);
        await wait(function() {
          expect(document.getElementById("data").value).toEqualCaseInsensitive("firstName,lastName;test,test");
        });
     });
});
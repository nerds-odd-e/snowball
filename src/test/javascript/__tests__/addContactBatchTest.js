const { wait } = require("@testing-library/dom");
const addContactBatchFunctions = require("../../../main/webapp/resources/js/addContactBatch.js");

describe('file upload function', function() {
     it('should format csv data', async function() {
        const markup = "<form><input id='data'/></form>";

        const container = document.createElement('div');
     	document.body.appendChild(container);
     	container.innerHTML = markup;

        const value = "firstName,lastName\n"+"test,test";
        addContactBatchFunctions.formatCsvDataExp(value);
        await wait(function() {
          expect(document.getElementById("data").value).toEqualCaseInsensitive("firstName,lastName;test,test");
        });
     });
});
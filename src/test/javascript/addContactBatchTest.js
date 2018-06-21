describe('file upload function', function(){
     it('should format csv data',function(){
        var markup = "<form><input id='data'/></form>";

        var container = document.createElement('div');
     	document.body.appendChild(container);
     	container.innerHTML = markup;

        var value = "firstName,lastName\n"+"test,test";
        formatCsvData(value);
        expect(document.getElementById("data").value).toBe("firstName,lastName;test,test");
     });
});
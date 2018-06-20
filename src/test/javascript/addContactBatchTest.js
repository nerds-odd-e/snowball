describe('fileInputHandler function', function(){
        var markup = "<input type='file' id='batchFile' onChange='fileInputHandler()' accept='.csv'/>";

     	beforeEach(function(){
     		var container = document.createElement('div');
     	    document.body.appendChild(container);
     	    container.innerHTML = markup;
     	});

     it('should not have file selected if format is not csv',function(){
//         	    var file = new File([''], 'test-image.png', {
//                  lastModified: 1449505890000,
//                  lastModifiedDate: new Date(1449505890000),
//                  name: "ecp-logo.png",
//                  size: 44320,
//                  type: "image/png",
//                });
          	    document.getElementById("batchFile").files[0] = file;
          //fileInputHandler()
     	  //expect(document.getElementById("batchFile").value).toBe("");
        });
});
describe('fileInputHandler function', function(){
     var markup = "<input type='file' id='batchFile' onChange='fileInputHandler()' accept='.csv'/>";

     beforeEach(function(){
     	var container = document.createElement('div');
     	document.body.appendChild(container);
     	container.innerHTML = markup;
     });

     it('should submit uploaded data',function(){

     });
});
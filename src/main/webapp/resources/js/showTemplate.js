var templateList = [];

$(document).ready(function() {

//	$("#applytemplate_button").click(function() {
//		var tem = new Object();
//		tem.Subject = "Hi , {FirstName}";
//		tem.Content = "Hey , {Company}";
//		ApplyTemplateToUI(tem);
//	});

    retrieveTemplateListFromServer(function(data) {
        templateList = data;
    });
});

function retrieveTemplateListFromServer(callback)
{
	$.ajax({
	    type: 'GET',
	    url: 'templates',
	    dataType: 'json',
	    success: function(data) { callback(data) }
	});
}

function renderTemplateList(json, selector)
{
	selector.append($('<option>').text('No Template').attr('value', 0).attr('id', 'template_0'));
	
	$.each(json, function(idx, item) {
		selector.append($('<option>').text(item.TemplateName).attr('value', item.Id).attr('id', 'template_'+ item.Id));
	})
}

function ApplyTemplateToUI(templateID)
{
    for (var i = 0; i < templateList.length; i++) {
        if (templateList[i].Id == templateID) {
            var selectedTemplate = templateList[i];

            $("#content").val(templateList[i].Content);
            $("#subject").val(templateList[i].Subject);

            return;
        }
    }
}


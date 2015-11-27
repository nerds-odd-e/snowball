$(document).ready(function() {

	$("#applytemplate_button").click(function() {
		var tem = new Object();
		tem.Subject = "Hi , {FirstName}";
		tem.Content = "Hey , {Company}";
		ApplyTemplateToUI(tem);
	});

});

function retrieveTemplateListFromServer()
{
	var templateList = [];

	$.ajax({
	    type: 'GET',
	    url: 'templates',
	    dataType: 'json',
	    success: function(data) {templateList = data },
	    async: false
	});

	return templateList;
}

function renderTemplateList(json, selector)
{
	selector.append($('<option>').text('No Template').attr('value', 0).attr('id', 'template_0'));
	
	$.each(json, function(idx, item) {
		selector.append($('<option>').text(item.TemplateName).attr('value', item.Id).attr('id', 'template_'+ item.Id));
	})
}

function ApplyTemplateToUI(template)
{
	$("#subject").val(template.Subject);
	$("#content").val(template.Content);
}

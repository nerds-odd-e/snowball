<!-- Modal -->
<div class="modal fade" id="selectContactModal" role="dialog">
  <div class="modal-dialog">
  
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Contacts</h4>
      </div>
      <div class="modal-body">
        <div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
                    <table class="table table-responsive table-striped">
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>Email</th>
                                <th>Name</th>
                                <th>Last Name</th>
                                <th>Company</th>
                                <th>Location</th>
                            </tr>
                        </thead>
                        <tbody id="selectContactTable">
                        </tbody>
                    </table>
			</div>
		</div>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-default" id="add_contact_button">Add</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
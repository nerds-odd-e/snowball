<!-- Contains the header and left pane which was common across the Send Email, Add Contact, Add Course, Contact List, and Email Tracking -->
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html">Massive Mailer</a>
        </div>
        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">
            <li class="dropdown"><a href="#" class="dropdown-toggle"
                data-toggle="dropdown"><i class="fa fa-envelope"></i> Terry <b
                    class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a></li>
                    <li><a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a></li>
                    <li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a></li>
                    <li class="divider"></li>
                    <li><a href="#"><i class="fa fa-fw fa-power-off"></i> Log
                            Out</a></li>
                </ul></li>
        </ul>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
                <li><a href="sendemail.jsp"><span
                        class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                        Edit Template</a></li>
                <li><a href="contactlist.jsp"><span
                        class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        Contact List</a></li>
                <ul><li><a href="add_contact.jsp"><span
                                        class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add
                                        Contact</a></li>
                      <li><a href="add_contact_batch.jsp"><span
                                                              class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add
                                                              Contact Batch</a></li>
                                        </ul>

                <li><a href="course_list.jsp"><span
                        class="glyphicon glyphicon-file" aria-hidden="true"></span> Course List
                        </a></li>
                <ul><li><a href="add_course.jsp"><span
                                       class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Course
                                       </a></li></ul>
                <li><a href="enrollParticipant.jsp"><span
                        class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        Enroll Participant</a></li>
                <li><a id="emailtracking" href="email_tracking.jsp"><span
                        class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        Email Tracking</a></li>
                <li><a id="GDPR" href="gdpr.jsp"><span
                        class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        GDPR</a></li>
            </ul>
        </div>
    <!-- /.navbar-collapse -->
    </nav>


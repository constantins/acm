<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" href="./resources/jquery/css/ui-lightness/jquery-ui-1.8.17.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="./resources/jquery/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./resources/jquery/js/jquery-ui-1.8.17.custom.min.js"></script>

<style type="text/css" title="currentStyle">
	@import "./resources/DataTables-1.9.0/media/css/demo_page.css";
	@import "./resources/DataTables-1.9.0/media/css/demo_table.css";
</style>
<script type="text/javascript" language="javascript" src="./resources/DataTables-1.9.0/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript" src="./resources/jquery/periodicalupdater/jquery.periodicalupdater.js"></script>
<style type="text/css">
	.ui-resizable-se {
		bottom: 17px;
	}
	#amFollowingPanel .ui-selecting { background: #FECA40; }
	#amFollowingPanel .ui-selected { background: #F39814; color: white; }
	#amFollowingPanel { list-style-type: none; margin: 0; padding: 0; width: 60%; }
	#amFollowingPanel li { margin: 3px; padding: 0.4em; font-size: 1.4em; height: 18px; }
</style>
<script type="text/javascript">

$(document).ready(function() {


	var pathnames = $(location).attr('pathname').split('/');
	var rootPath = pathnames[1]; // acm
	var url = "/" + rootPath + '/user/isLogin';

	$.ajax({
		url: url,
		async: false,
		success: function(data){
			if (data.status == 'false') {
	   			window.location.replace("/" + rootPath);
	   		} else {
	   			$('#loginName').html(data.message);
	   		}
		}
	});

    /* Init the table */
    oTable = $("#myAccountsPanel").dataTable();

    /* Add a click handler to the rows - this could be used as a callback */
    $("#myAccountsPanel tbody").click(function(event) {
        $(oTable.fnSettings().aoData).each(function (){
            $(this.nTr).removeClass('row_selected');
        });
        $(event.target.parentNode).addClass('row_selected');
    });

    /* Get the rows which are currently selected */
    function fnGetSelected( oTableLocal )
    {
        var aReturn = new Array();
        var aTrs = oTableLocal.fnGetNodes();

        for ( var i=0 ; i<aTrs.length ; i++ )
        {
            if ( $(aTrs[i]).hasClass('row_selected') )
            {
                aReturn.push( aTrs[i] );
            }
        }
        return aReturn;
    }

	// populate accounts panel
	var allAccountsUrl = "/" + rootPath + "/account/list";
	$.get(allAccountsUrl,
		function(data) {
			for (var i = 0; i < data.length; i++) {
				var item = data[i];
				$( '#myAccountsPanel').dataTable().fnAddData( [
					item.number
				] );
			}
		}
	);

  	function updateTips( t, tips) {
		tips.text( t )
			.addClass( "ui-state-highlight" );
		setTimeout(function() {
			tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	};

  	$( "#logout" )
	.button()
	.click(function() {
		$.get("/" + rootPath + '/user/logout', function(data){
			window.location.replace("/" + rootPath);
		});
	});

  	var tips = $( ".validateTips" );

  	$( "#enterAccount" )
	.button()
	.click(function() {
        var anSelected = fnGetSelected( oTable );
        var nr = oTable.fnGetData( anSelected[0]).toString();
		if (nr.indexOf(',')  < 0) {
			var url = "/" + rootPath + '/account/accountList';
			$.post(url,
                {accountnumber: nr},
				function(data) {
                    var pathname = window.location.pathname;
                    window.location.replace("/" + rootPath + "/home");
			});
		} else {
			updateTips( "Please select an account", tips);
		}
	});

  	function split( val ) {
		return val.split( /,\s*/ );
	}
	function extractLast( term ) {
		return split( term ).pop();
	}


});

</script>
</head>

<body>
<table border="1" style="width: 60%">
<tr>
    <td>Welcome <span id="loginName"></span>
		<p><input type='button' id='logout' value='Logout'><p/>
	</td>
</tr>
<tr>
	<td>
		<h3>Accounts</h3>
        <p class="validateTips"></p>
		<table id="myAccountsPanel" class="display">
			<thead>
				<tr><th>Number</th></tr>
			</thead>
			<tbody>
			</tbody>
		</table>
        <input type='button' id='enterAccount' value='Enter account'>
	</td>
</tr>
</table>

</body>
</html>
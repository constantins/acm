<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="./resources/jquery/css/ui-lightness/jquery-ui-1.8.17.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="./resources/jquery/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./resources/jquery/js/jquery-ui-1.8.17.custom.min.js"></script>
		
<style>
	label, input { display:block; }
	input.text { margin-bottom:12px; width:95%; padding: .4em; }
	fieldset { padding:0; border:0; margin-top:25px; }
	.ui-dialog .ui-state-error { padding: .3em; }
	.validateTips { border: 1px solid transparent; padding: 0.3em; }
	.loginValidateTips { border: 1px solid transparent; padding: 0.3em; }	
</style>
<title>Demo account manager</title>
<script type="text/javascript">
	$(document).ready(function() {
		var pathname = window.location.pathname;
		var url = '';
		
		if (pathname.lastIndexOf('/') === pathname.length -1) {
			url = pathname + 'user/isLogin'; 
		} else {
			url = pathname + '/user/isLogin';
		}
		
		$.get(url,
			function(data) {
				if (data.status == 'true') {					
					window.location.replace(pathname+ "home");					
			}
		});
		
		var tips = $( ".validateTips" );

		function updateTips( t, tips) {
			tips.text( t )
				.addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 5000 );
		}

		function checkRegexp( o, regexp, n, tips) {
			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass( "ui-state-error" );
				updateTips( n, tips);
				return false;
			} else {
				return true;
			}
		}

		var login_id = $( "#login_id" ),
			login_allFields = $( [] ).add( login_id ),
			loginTips = $( ".loginValidateTips" );

		$( "#login-form-submit" )
		.button()
		.click(function() {
			// validate input and then submit the form
			var bValid = true;
					login_allFields.removeClass( "ui-state-error" );
					bValid = bValid && checkRegexp( login_id, /^([0-9])+$/, "UserID must be a number", loginTips);

					if ( bValid ) {
						// send request to server
						$.ajax({
  							url: window.location.pathname + "user/checkLogin",
  							type: 'POST',
  							data: {userid: login_id.val()},
  							async: false,
  							success: function(data){
  								if (data.status == "true") {
  									var pathname = window.location.pathname;
  									window.location.replace(pathname+ "list");
  								} else {
  									updateTips('Wrong UserID', loginTips);
  								}   							
  							}
						});
					}
		});
	});
	
		
</script>
</head>
<body>
	<h1 align="center">Account Manager</h1>
    <p class="loginValidateTips"></p>
	<div id='effect' style="width:400px">
	<form name="loginForm" id="login-form" action="#" method="post">
		<label>UserID</label><input type="text" id="login_id" name="userid" class="text ui-widget-content ui-corner-all">
		<input id='login-form-submit' type="button" value="Login">
	</form>
	</div>

</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" href="./resources/jquery/css/ui-lightness/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
<script type="text/javascript" src="./resources/jquery/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./resources/jquery/js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="./resources/jquery/js/NumberFormat154.js"></script>

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
    var storredSold = 0;

    <%--username--%>
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

    <%--accountinfo--%>
    $.ajax({
        url: "/" + rootPath + '/account/accountDetails',
        async: false,
        success: function(data){
            $('#account_number').val(data.number);
            $('#account_sold').val(data.sold);
            formatNumber("account_sold");
            storredSold = data.sold;
        }
    });

    function updateTips( t, tips) {
        tips.text( t )
                .addClass( "ui-state-highlight" );
        setTimeout(function() {
            tips.removeClass( "ui-state-highlight", 1500 );
        }, 5000 );
    };

    $( "#logout" )
            .button()
            .click(function() {
                $.get("/" + rootPath + '/user/logout', function(data){
                    window.location.replace("/" + rootPath);
                });
            });

    var tips = $( ".validateTips" );

    $( "#executeOperation" )
            .button()
            .click(function() {
                // validate input and then submit the form
                var bValid = true;
                var so = $('#account_sold').val();
                var am = $('#account_amount').val();
                var op = $('input[name=operation]:checked').val();
                bValid = bValid && checkRegexp( $('#account_amount'), /^([0-9])+$/, "The field <i>Amount</i> must contain only digits", tips);
                if (op=="withdraw") {
                    bValid = bValid && checkSold($('#account_amount'), am, "Insufficient money for the withdrawal", tips);
                }

                if (am.length > 0 && op!=null && bValid) {
                    var url = "/" + rootPath + '/account/executeOperation';
                    $.post(url,
                            {account_operation: op, account_amount: am},
                            function(data) {
                                var pathname = window.location.pathname;
                                window.location.replace("/" + rootPath + "/home");
                            });
                } else if(am.length < 1){
                    updateTips( "Please fill up the amount", tips);
                } else if(op==null){
                    updateTips( "Please select operation type", tips);
                }
            });

    $( "#return_to_list" )
            .button()
            .click(function() {
                    var url = "/" + rootPath + '/account/list';
                    $.get(url,
                            function(data) {
                                var pathname = window.location.pathname;
                                window.location.replace("/" + rootPath + "/list");
                            });

            });

    function split( val ) {
        return val.split( /,\s*/ );
    }
    function extractLast( term ) {
        return split( term ).pop();
    }

    function formatNumber(objId)
    {
        var obj = document.getElementById(objId);
        var num = new NumberFormat();
        num.setInputDecimal(',');
        num.setNumber(obj.value); // obj.value is '1000247'
        num.setPlaces('0', false);
        num.setCurrencyValue('$');
        num.setCurrency(false);
        num.setCurrencyPosition(num.LEFT_OUTSIDE);
        num.setNegativeFormat(num.LEFT_DASH);
        num.setNegativeRed(false);
        num.setSeparators(true, '.', ',');
        obj.value = num.toFormatted();

    }

    function checkRegexp(o, regexp, n, tips) {
        if (!( regexp.test(o.val()) )) {
            o.addClass("ui-state-error");
            updateTips(n, tips);
            return false;
        } else {
            return true;
        }
    }

    function checkSold(o, withdrawal, n, tips) {
        if (withdrawal > storredSold) {
            o.addClass("ui-state-error");
            updateTips(n, tips);
            return false;
        } else {
            return true;
        }
    }
});

</script>
</head>

<body>
<table border="1" style="width: 90%">
    <tr>
        <td>Welcome <h2 id="loginName"></h2>
            <p><input type='button' id='logout' value='Logout'><p/>
        </td>
    </tr>
    <tr>
        <td>
            <h3>Account Details</h3>
            <p class="validateTips"></p>
            <div><label>Number</label><input type="text" id="account_number" name="account_number" class="text ui-widget-content ui-corner-all" readonly="true"/></div>
            <div><label>Sold</label><input type="text" id="account_sold" name="account_sold" class="text ui-widget-content ui-corner-all" readonly="true"/></div>
            <div><label>Amount</label><input type="text" id="account_amount" name="account_amount" class="text ui-widget-content ui-corner-all"/></div>
            <div>
                <input type="radio" name="operation" value="deposit">Deposit</input>
                <input type="radio" name="operation" value="withdraw">Withdrawal</input>
                <input type='button' id='executeOperation' value='Execute operation'/>
            </div>
            <div>
                <input type='button' id='return_to_list' value='Return'/>
            </div>
        </td>
    </tr>
</table>

</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Examples</title>
    <style type="text/css">
        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 350px;
            overflow-y: scroll;
            padding: 5px;
            width: 100%;
        }

        #console p {
            padding: 0;
            margin: 0;
        }
        
    </style>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        var ws = null;
		function setConnected(connected) {
			document.getElementById('connect').disabled = connected;
			document.getElementById('disconnect').disabled = !connected;
		}
        
        function connect() {
            if ('WebSocket' in window) {
                ws = new WebSocket('ws://0.0.0.0:8080/test/ping');
            } else if ('MozWebSocket' in window) {
                ws = new MozWebSocket('ws://0.0.0.0:8080/test/ping');
            } else {
                alert('WebSocket is not supported by this browser.');
                return;
            }
            ws.onopen = function () {
                setConnected(true);
                log('Info: WebSocket connection opened.');
            };
            ws.onmessage = function (event) {
            	//var info = jQuery.parseJSON(event.data);
                //log('Received: ' + info.feedname + ' feed with ' + info.itemsCount + ' items');
                log(event.data);
            };
            ws.onclose = function () {
                setConnected(false);
                log('Info: WebSocket connection closed.');
            };
        }

        function disconnect() {
            if (ws != null) {
                ws.close();
                ws = null;
            }
            setConnected(false);
        }

        function echo() {
            if (ws != null) {
                var message = document.getElementById('message').value;
                log('Sent: ' + message);
                ws.send(message);
            } else {
                alert('WebSocket connection not established, please connect.');
            }
        }

        function log(message) {
        	$("#console").prepend('<p class="new">' + message + '</p>');
        }
        
        $(window).focus(function() {
            document.title = 'WebSocket Examples';
            $(".new").animate({ opacity: 0.25 }, 5000);
        });
    </script>
</head>
<body>
<div>
	<div>
    	<button id="connect" onclick="connect();">Connect</button>
    	<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
    </div>
    <div id="console-container">
        <div id="console"></div>
    </div>
    <div><input type="text" id="message" /><button id="echo" onclick="echo();">Send message</button></div>
</div>
</body>
</html>

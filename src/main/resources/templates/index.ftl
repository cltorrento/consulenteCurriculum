<!DOCTYPE html>
<html>

<head>
    <link rel="styleSheet" href="http://ui-grid.info/release/ui-grid.min.css" />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" />
    <link href="css/style.css" rel="stylesheet"/>

    <script type="text/javascript" src="https://cdn.rawgit.com/adonespitogo/angular-base64-upload/master/src/angular-base64-upload.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular-animate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular-touch.min.js"></script>
    <script src="http://ui-grid.info/release/ui-grid.js"></script>
    <script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.13.4.js"></script>
    <script src="js/app/script.js"></script>
    <script src="js/app/base64App.js"></script>
</head>

<body ng-app="influx">
    <div ng-controller="MainCtrl as vm">
        <div style="padding-left: 30px;">
            <button type="button" id="addRow" class="btn btn-success" ng-click="addRow()">Nuovo Consulente</button>
        </div>
        <br />
       <div id="serviceGrid" 
        ui-grid-selection
        ui-grid-pinning
        ui-grid-edit
        ui-grid-pagination
        ui-grid="vm.serviceGrid" class="influxGrid"></div>
    </div>
    
</body>

</html>

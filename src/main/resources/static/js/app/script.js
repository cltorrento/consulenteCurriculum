var app = angular.module('influx', 
	[
		'ngAnimate', 'ui.grid', 'ui.grid.moveColumns', 'ui.grid.selection', 'ui.grid.resizeColumns', 
		'ui.bootstrap', 'ui.grid.edit','ui.grid.pagination'
	])

app.controller('MainCtrl', MainCtrl);
app.controller('RowEditCtrl', RowEditCtrl);
app.service('RowEditor', RowEditor);

MainCtrl.$inject = [ '$scope', '$http', '$modal', 'RowEditor', 'uiGridConstants' ];
function MainCtrl($scope, $http, $modal, RowEditor, uiGridConstants) {
	var vm = this;

	vm.editRow = RowEditor.editRow;

	vm.serviceGrid = {
		enableRowSelection : true,
		enableRowHeaderSelection : false,
		multiSelect : false,
		enableSorting : true,
		enableFiltering : true,
		enableGridMenu : true,
		pagination : true,
		paginationPageSize : 10,
		paginationPageSizes: [5, 10, 25],
		rowTemplate : "<div ng-dblclick=\"grid.appScope.vm.editRow(grid, row)\" ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell></div>"
	};

	vm.serviceGrid.columnDefs = [ {
		field : 'codiceFiscale',
		enableSorting : true,
		width : 200,
		enableCellEdit : false
	}, {
		field : 'nome',
		enableSorting : true,
		enableCellEdit : false
	}, {
		field : 'cognome',
		enableSorting : true,
		enableCellEdit : false
	}, {
		field : 'titolo',
		width : 120,
		enableSorting : true,
		enableCellEdit : false
	},{
		field : 'ral',
		type : 'number',
		enableSorting : true,
		enableCellEdit : false
	},{
		field : 'seniority',
		enableSorting : true,
		enableCellEdit : false
	}, {
		field : 'skills',
		enableSorting : true,
		enableCellEdit : false
	}, {
		field : 'cv.filename',
		displayName : 'Nome di File',
		enableSorting : true,
		width : 200,
		enableCellEdit : false
	}
	/*,{
		field : 'cv',
		enableFiltering: false,
		displayName : 'PDF',
		width: 50,
		cellTemplate: 'cv.html'
	}*/
	];

	$http.get('http://localhost:9000/consulente/list/').success(function(response) {
		vm.serviceGrid.data = response;
	});

	$scope.addRow = function() {
		var newService = {};
		var rowTmp = {};
		rowTmp.entity = newService;
		vm.editRow($scope.vm.serviceGrid, rowTmp);
	};
}


RowEditor.$inject = [ '$http', '$rootScope', '$modal' ];
function RowEditor($http, $rootScope, $modal) {
	var service = {};
	service.editRow = editRow;

	function editRow(grid, row) {
		$modal.open({
			templateUrl : 'form.html',
			controller : [ '$http', '$modalInstance', 'grid', 'row', RowEditCtrl ],
			controllerAs : 'vm',
			resolve : {
				grid : function() {
					return grid;
				},
				row : function() {
					return row;
				}
			}
		});
	}
	return service;
}

function RowEditCtrl($http, $modalInstance, grid, row) {
	var vm = this;
	vm.entity = angular.copy(row.entity);
	vm.save = save;

	function save() {
		row.entity = angular.extend(row.entity, vm.entity);
		if (vm.entity.codiceFiscale === undefined || vm.entity.codiceFiscale === null) {
			alert('Complete the Information');			
		} else {
			createConsultant(row.entity);
		}
		$modalInstance.close(row.entity);
	}

	function createConsultant(cons){
		console.log('Insert the new Consultant');

		str = JSON.stringify(cons, null, 4); 
		alert(str);



		$http.post('http://localhost:9000/consulente/insert/', cons)
		.success(
		function(response) { 
			alert('Operation has been processed Sucessfully...');
			$modalInstance.close(row.entity);
		}).error(
		function(response) {
			console.error('Error to insert the new Consultant');
			alert('Error to insert the new Consultant'); 
			console.dir(response); 
		});
		grid.data.push(cons);
	}


	vm.remove = remove;
	function remove() {
		console.dir(row);
		if (vm.entity.codiceFiscale != null) {
			if (confirm("Are you Sure to DELETE the Consultant?")) {
				row.entity = angular.extend(row.entity, vm.entity);
				var index = grid.appScope.vm.serviceGrid.data.indexOf(row.entity);
				grid.appScope.vm.serviceGrid.data.splice(index, 1);
			
				$http.delete('http://localhost:9000/consulente/delete/' + vm.entity.codiceFiscale)
				.success(
					function(response) {
						alert('Record Successfully DELETED..');
						$modalInstance.close(row.entity);
					}).error(
						function(response) {
							alert('Cannot delete row (error in console)');
							console.dir(response); 
						});
			}
			$modalInstance.close(row.entity);
		}
	}
}
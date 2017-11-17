app.controller('appController', function ($scope, $window) {
    var data = $scope.entity.cv.base64,
        url = $window.URL || $window.webkitURL;
    $scope.fileUrl = url.createObjectURL(b64toBlob(data, 'application/pdf')) ;
});


function b64toBlob(b64Data, contentType) {
	contentType = contentType || '';
	var sliceSize = 512;
	b64Data = b64Data.replace(/^[^,]+,/, '');
	b64Data = b64Data.replace(/\s/g, '');
	var byteCharacters = window.atob(b64Data);
	var byteArrays = [];

	for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
		var slice = byteCharacters.slice(offset, offset + sliceSize);

		var byteNumbers = new Array(slice.length);
		for (var i = 0; i < slice.length; i++) {
			byteNumbers[i] = slice.charCodeAt(i);
		}

		var byteArray = new Uint8Array(byteNumbers);
		byteArrays.push(byteArray);
	}

	var blob = new Blob(byteArrays, {type: contentType});
	return blob;
}
'use strict';

angular.module('silq2App')
    .filter('bytes', function () {
    	return function(bytes, precision) {
            bytes = parseFloat(bytes);

            if (bytes === 0) {
                return '0.0 bytes';
            }

    		if (isNaN(bytes) || !isFinite(bytes)) {
                return '-';
            }

    		if (typeof precision === 'undefined') {
                precision = 1;
            }

    		var units = ['bytes', 'kB', 'MB', 'GB', 'TB', 'PB'];
    		var number = Math.floor(Math.log(bytes) / Math.log(1024));

    		return (bytes / Math.pow(1024, Math.floor(number))).toFixed(precision) +  ' ' + units[number];
    	};
    });

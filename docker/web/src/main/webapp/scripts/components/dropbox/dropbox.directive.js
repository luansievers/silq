'use strict';

angular.module('silq2App')
    .directive('dropbox', function(Upload, Flash) {
        return {
            restrict: 'E',
            scope: {
                config: '=config',
                files: '=files',
                success: '=?success',
                multiple: '=?multiple'
            },
            transclude: true,
            templateUrl: 'scripts/components/dropbox/dropbox.html',
            compile: function(element, attrs) {
                attrs.multiple = angular.isDefined(attrs.multiple) ? attrs.multiple : 'true';
            },
            controller: function($rootScope, $scope) {
                $scope.success = $scope.success || function() {};

                if (!$scope.config.data) {
                    $scope.config.data = {};
                }

                $scope.uploadFiles = function(files) {
                    if (!files) return;
                    files.forEach(function(file) {
                        $scope.files.push(file);
                        file.status = 'uploading';

                        var params = JSON.parse(JSON.stringify($scope.config)); // clone config
                        params.data.file = file;

                        // Não mostramos o loader para uploads já que o
                        // dropbox mostra uma barra de progresso
                        params.loadingIndicator = false;

                        Upload.upload(params)
                            .then(function (resp) {
                                file.status = 'success';
                                $scope.success(resp);
                            }, function (resp) {
                                Flash.create('danger', '<strong>Ops!</strong> Ocorreu um erro ao processar o arquivo "' + file.name + '".');
                                file.status = 'error';
                                console.error(resp);
                            }, function (evt) {
                                file.progress = parseInt(100.0 * evt.loaded / evt.total);
                            });
                    });
                };
            }
        };
    });

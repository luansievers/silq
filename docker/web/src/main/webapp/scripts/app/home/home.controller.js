'use strict';

angular.module('silq2App')
    .controller('HomeController', function ($scope, $state, Principal, Upload, CurriculumLattes, Cache, Flash, AvaliarConfigModal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
        });

        CurriculumLattes.get().then(function(resp) {
            var dados = resp.data;
            if (dados.nome) {
                $scope.curriculum = dados;
            } else {
                $scope.curriculum = null;
            }
        });

        $scope.minhaAvaliacao = function() {
            AvaliarConfigModal.open({
                area: $scope.curriculum.areaConhecimento
            }).then(function(form) {
                $state.go('avaliacao.individual', {
                    id: $scope.curriculum.id,
                    avaliarForm: form
                });
            });
        };

        $scope.atualizar = function() {
            $scope.curriculum = null;
        };

        $scope.remover = function() {
            CurriculumLattes.delete().then(function() {
                $scope.curriculum = null;
                Flash.create('success', '<strong>Sucesso!</strong> Currículo removido');
            });
        };

        // Dropbox configuration
        $scope.files = [];
        $scope.uploadConfig = {
            url: CurriculumLattes.url
        };
        $scope.uploaded = function(resp) {
            $scope.files = [];
            $scope.curriculum = resp.data;
            Cache.invalidate();
            Flash.create('success', '<strong>Sucesso!</strong> Currículo enviado');
        };
    });

'use strict';

angular.module('silq2App')
    .factory('Grupo', function ($resource, $http, Cache) {
        return {
            query: function() {
                return $http.get('api/grupos/', {
                    cache: true
                });
            },

            get: function(grupoId) {
                return $http.get('api/grupos/' + grupoId, {
                    cache: true
                });
            },

            create: function(grupo) {
                Cache.invalidate();
                return $http.post('api/grupos/', grupo);
            },

            update: function(grupo) {
                Cache.invalidate();
                return $http.put('api/grupos', grupo);
            },

            delete: function(grupoId) {
                Cache.invalidate();
                return $http.delete('api/grupos/' + grupoId);
            },

            share: function(grupoId, email) {
                Cache.invalidate();
                return $http.post('api/grupos/' + grupoId + '/share', email);
            },

            removeEspectador: function(grupoId, espectadorId) {
                Cache.invalidate();
                return $http.delete('api/grupos/' + grupoId + '/removeEspectador/' + espectadorId);
            },

            removePesquisador: function(grupoId, pesquisadorId) {
                Cache.invalidate();
                return $http.delete('api/grupos/' + grupoId + '/removePesquisador/' + pesquisadorId);
            },

            avaliarPesquisador: function(grupoId, pesquisadorId) {
                return $http.get('api/grupos/'+grupoId+'/avaliar/' + pesquisadorId, {
                    cache: true
                });
            },

            avaliarGrupo: function(grupoId, avaliarForm) {
                return $http.post('api/avaliar/grupo/' + grupoId, avaliarForm);
            },

            classificarGrupo: function(grupoId, avaliarForm) {
                return $http.post('api/classificar/grupo/' + grupoId, avaliarForm);
            }
        };
    });

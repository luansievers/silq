'use strict';

angular.module('silq2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('avaliar', {
                parent: 'site',
                url: '/principal/avaliar',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/principal/avaliar/avaliar.html',
                        controller: 'AvaliarController'
                    },
                    'content-header@': {
                        templateUrl: 'scripts/components/content-header/content-header.html',
                        controller: 'ContentHeaderController'
                    }
                },
                resolve: {

                }
            });
    });

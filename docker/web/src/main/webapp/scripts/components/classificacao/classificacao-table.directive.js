'use strict';

angular.module('silq2App')
    .directive('classificacaoTable', function() {
        return {
            restrict: 'E',
            scope: {
                avaliacoes: '=?avaliacoes'
            },
            templateUrl: 'scripts/components/classificacao/classificacao-table.html',
            link: function($scope) {
                $scope.obterPontuacao = function(artigos, trabalhos) {
                    var CONCEITO_NOTA_ENUM = {
                        'A1': 100,
                        'A2': 85,
                        'B1': 70,
                        'B2': 0,
                        'B3': 0,
                        'B4': 0,
                        'B5': 0,
                        'C': 0,
                        'sem-conceito': 0,
                        'total': 0
                    };
                    var anoLimite = new Date().getFullYear()-4;
                    var nota = 0;
                    for(var index in artigos) {
                        if(Number(index) >= anoLimite){
                            var conceitos = artigos[index];
                            for(var conceito in conceitos) {
                                nota = nota +  (CONCEITO_NOTA_ENUM[conceito] * conceitos[conceito])
                            }
                        }
                    }
                    for(var index in trabalhos) {
                        if(Number(index) >= anoLimite){
                            var conceitos = trabalhos[index];
                            for(var conceito in conceitos) {
                                nota = nota + (CONCEITO_NOTA_ENUM[conceito] * conceitos[conceito])
                            }
                        }
                    }
                    return nota;
                };

                $scope.obterClassificacao = function(artigos, trabalhos) {
                    var nota = $scope.obterPontuacao(artigos, trabalhos);
                    var quantidadeArtigos = 0;
                    var anoLimite = new Date().getFullYear()-4;
                    debugger;
                    for(var index in artigos) {
                        if(Number(index) >= anoLimite){
                            quantidadeArtigos = quantidadeArtigos + artigos[index]['total']
                        }
                    }
                    if(nota >= 200 && quantidadeArtigos >= 3){
                        return 'GRUPO I'
                    }else if(nota >= 200 && quantidadeArtigos >= 2){
                        return 'GRUPO II'
                    }else if(nota >= 200 && quantidadeArtigos >= 1){
                        return 'GRUPO III'
                    }else{
                        return 'SEM GRUPO'
                    }
                }
            }
        };
    });

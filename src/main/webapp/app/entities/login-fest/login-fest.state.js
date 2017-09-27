(function() {
    'use strict';

    angular
        .module('codeFestApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('login-fest', {
            parent: 'entity',
            url: '/login-fest',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LoginFests'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/login-fest/login-fests.html',
                    controller: 'LoginFestController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('login-fest-detail', {
            parent: 'login-fest',
            url: '/login-fest/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LoginFest'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/login-fest/login-fest-detail.html',
                    controller: 'LoginFestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LoginFest', function($stateParams, LoginFest) {
                    return LoginFest.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'login-fest',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('login-fest-detail.edit', {
            parent: 'login-fest-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-fest/login-fest-dialog.html',
                    controller: 'LoginFestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoginFest', function(LoginFest) {
                            return LoginFest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('login-fest.new', {
            parent: 'login-fest',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-fest/login-fest-dialog.html',
                    controller: 'LoginFestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userName: null,
                                password: null,
                                rights: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('login-fest', null, { reload: 'login-fest' });
                }, function() {
                    $state.go('login-fest');
                });
            }]
        })
        .state('login-fest.edit', {
            parent: 'login-fest',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-fest/login-fest-dialog.html',
                    controller: 'LoginFestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoginFest', function(LoginFest) {
                            return LoginFest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('login-fest', null, { reload: 'login-fest' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('login-fest.delete', {
            parent: 'login-fest',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-fest/login-fest-delete-dialog.html',
                    controller: 'LoginFestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LoginFest', function(LoginFest) {
                            return LoginFest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('login-fest', null, { reload: 'login-fest' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

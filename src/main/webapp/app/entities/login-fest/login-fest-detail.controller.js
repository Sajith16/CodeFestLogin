(function() {
    'use strict';

    angular
        .module('codeFestApp')
        .controller('LoginFestDetailController', LoginFestDetailController);

    LoginFestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LoginFest'];

    function LoginFestDetailController($scope, $rootScope, $stateParams, previousState, entity, LoginFest) {
        var vm = this;

        vm.loginFest = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('codeFestApp:loginFestUpdate', function(event, result) {
            vm.loginFest = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

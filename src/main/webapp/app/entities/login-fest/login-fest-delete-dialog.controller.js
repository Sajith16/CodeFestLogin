(function() {
    'use strict';

    angular
        .module('codeFestApp')
        .controller('LoginFestDeleteController',LoginFestDeleteController);

    LoginFestDeleteController.$inject = ['$uibModalInstance', 'entity', 'LoginFest'];

    function LoginFestDeleteController($uibModalInstance, entity, LoginFest) {
        var vm = this;

        vm.loginFest = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LoginFest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('codeFestApp')
        .controller('LoginFestDialogController', LoginFestDialogController);

    LoginFestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LoginFest'];

    function LoginFestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LoginFest) {
        var vm = this;

        vm.loginFest = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.loginFest.id !== null) {
                LoginFest.update(vm.loginFest, onSaveSuccess, onSaveError);
            } else {
                LoginFest.save(vm.loginFest, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('codeFestApp:loginFestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

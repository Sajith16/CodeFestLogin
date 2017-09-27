(function() {
    'use strict';

    angular
        .module('codeFestApp')
        .controller('LoginFestController', LoginFestController);

    LoginFestController.$inject = ['LoginFest'];

    function LoginFestController(LoginFest) {

        var vm = this;

        vm.loginFests = [];

        loadAll();

        function loadAll() {
            LoginFest.query(function(result) {
                vm.loginFests = result;
                vm.searchQuery = null;
            });
        }
    }
})();

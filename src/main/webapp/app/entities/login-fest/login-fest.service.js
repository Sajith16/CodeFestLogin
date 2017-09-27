(function() {
    'use strict';
    angular
        .module('codeFestApp')
        .factory('LoginFest', LoginFest);

    LoginFest.$inject = ['$resource'];

    function LoginFest ($resource) {
        var resourceUrl =  'api/login-fests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

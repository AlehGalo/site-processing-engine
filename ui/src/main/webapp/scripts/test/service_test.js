'use strict';

informerApp.factory('Test', ['$resource',
    function ($resource) {
        return $resource('app/rest/tests/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    }]);

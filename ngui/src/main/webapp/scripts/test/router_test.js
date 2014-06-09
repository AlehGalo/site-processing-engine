'use strict';

informerApp
    .config(['$routeProvider', '$httpProvider', '$translateProvider', 'USER_ROLES',
        function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/test', {
                    templateUrl: 'views/tests.html',
                    controller: 'TestController',
                    resolve:{
                        resolvedTest: ['Test', function (Test) {
                            return Test.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        }]);

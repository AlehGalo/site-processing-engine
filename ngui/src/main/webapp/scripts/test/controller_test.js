'use strict';

informerApp.controller('TestController', ['$scope', 'resolvedTest', 'Test',
    function ($scope, resolvedTest, Test) {

        $scope.tests = resolvedTest;

        $scope.create = function () {
            Test.save($scope.test,
                function () {
                    $scope.tests = Test.query();
                    $('#saveTestModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.test = Test.get({id: id});
            $('#saveTestModal').modal('show');
        };

        $scope.delete = function (id) {
            Test.delete({id: id},
                function () {
                    $scope.tests = Test.query();
                });
        };

        $scope.clear = function () {
            $scope.test = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
        };
    }]);

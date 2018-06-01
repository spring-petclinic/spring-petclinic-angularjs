'use strict';

angular.module('vetList')
    .controller('VetListController', ['$http', function ($http) {
        const self = this;

        $http.get('vets').then(function (resp) {
            self.vetList = resp.data;
        });
    }]);

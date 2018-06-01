'use strict';

angular.module('ownerList')
    .controller('OwnerListController', ['$http', function ($http) {
        const self = this;

        $http.get('owners/list').then(function (resp) {
            self.owners = resp.data;
        });
    }]);

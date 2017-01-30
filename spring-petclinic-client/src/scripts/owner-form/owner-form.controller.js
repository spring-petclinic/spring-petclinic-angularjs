'use strict';

angular.module('ownerForm')
    .controller('OwnerFormController', ["$http", '$state', '$stateParams', function ($http, $state, $stateParams) {
        var self = this;

        var ownerId = $stateParams.ownerId || 0;

        if (!ownerId) {
            self.owner = {};
        } else {
            $http.get("owner/" + ownerId).then(function (resp) {
                self.owner = resp.data;
            });
        }

        self.submitOwnerForm = function () {
            var id = self.owner.id;

            var errorHandler = function (response) {
                var error = response.data;
                alert(error.error + "\r\n" + error.errors.map(function (e) {
                        return e.field + ": " + e.defaultMessage;
                    }).join("\r\n"));
            };

            if (id) {
                $http.put('owner/' + id, self.owner).then(function () {
                    $state.go('ownerDetails', {ownerId: ownerId});
                }, errorHandler);
            } else {
                $http.post('owner', self.owner).then(function () {
                    $state.go('owners');
                }, errorHandler);
            }
        };
    }]);

'use strict';

angular.module('petForm')
    .controller('PetFormController', ['$http', '$state', '$stateParams', function ($http, $state, $stateParams) {
        const self = this;
        const ownerId = $stateParams.ownerId || 0;

        $http.get('petTypes').then(function (resp) {
            self.types = resp.data;
        }).then(function () {

            const petId = $stateParams.petId || 0;

            if (petId) { // edit
                $http.get("owners/" + ownerId + "/pets/" + petId).then(function (resp) {
                    self.pet = resp.data;
                    self.pet.birthDate = new Date(self.pet.birthDate);
                    self.petTypeId = "" + self.pet.type.id;
                });
            } else {
                $http.get('owners/' + ownerId).then(function (resp) {
                    self.pet = {
                        owner: resp.data.firstName + " " + resp.data.lastName
                    };
                    self.petTypeId = "1";
                })

            }
        });

        self.submit = function () {
            const id = self.pet.id || 0;

            const data = {
                id: id,
                name: self.pet.name,
                birthDate: self.pet.birthDate,
                typeId: self.petTypeId
            };

            let req;
            if (id) {
                req = $http.put("owners/" + ownerId + "/pets/" + id, data);
            } else {
                req = $http.post("owners/" + ownerId + "/pets", data);
            }

            req.then(function () {
                $state.go('ownerDetails', {ownerId: ownerId});
            });
        };
    }]);

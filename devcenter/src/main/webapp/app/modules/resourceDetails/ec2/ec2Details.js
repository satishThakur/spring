/**
 * Created by satish on 24/06/15.
 */

(function(){
    'use strict';

    var ec2DetailsController = function($scope, ec2Instances){
        $scope.rowCollection = [];
        $scope.displayedCollection = [];
        $scope.itemsByPage=12;

        ec2Instances.query(function(data){
            $scope.rowCollection = data;
            console.log(data);
        });

    };

    angular.module('infraApp.dashboard').controller('ec2DetailsController',ec2DetailsController);

})();
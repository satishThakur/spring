/**
 * Created by satish on 24/06/15.
 */
(function(){
    'use strict';

    var ec2Instances = function($resource){
        return $resource('aws/instances');
    };
    var services = angular.module('infraApp.resources');
    services.factory('ec2Instances', ec2Instances);

})();
/**
 * Created by satish on 16/06/15.
 */

(function(){
    'use strict';

    var resourcesStat = function($resource){
        return $resource('resources/stats',{},
            {
                'query' : {method : 'GET', isArray : false}
            }
        );
    };

    var services = angular.module('infraApp.resources',['ngResource']);
    services.factory('resourcesStat', resourcesStat);

})();

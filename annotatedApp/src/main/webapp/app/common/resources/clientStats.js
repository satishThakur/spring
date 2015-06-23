/**
 * Created by satish on 23/06/15.
 */

(function(){
    'use strict';
    var clientStat = function($resource){
        return $resource('resources/stats/clients',{},
            {
                'query' : {method : 'GET', isArray : false}
            }
        );
    };

    var services = angular.module('infraApp.resources');
    services.factory('clientStat', clientStat);
})();
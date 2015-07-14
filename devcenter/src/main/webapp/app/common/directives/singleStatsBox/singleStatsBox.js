/**
 * Created by satish on 13/06/15.
 */
(function(){

    var singleStatBox = function(){
        return {
            restrict : 'E',
            replace : true,
            templateUrl: 'app/common/directives/singleStatsBox/singleStatsBox.html',
            scope : {
                panelStyle: '=',
                stats : '=',
                name : '@',
                state : '@',
                style : '='
            },
            link : function(scope, element, attrs){
            }

        };
    };


    var directives = angular.module('infraApp.directives', []);
    directives.directive('singleStatBox', singleStatBox);

})();
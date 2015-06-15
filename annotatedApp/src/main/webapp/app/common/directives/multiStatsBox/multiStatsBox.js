/**
 * Created by satish on 13/06/15.
 */
(function(){

    var multiStatBox = function(){
        return {
            restrict : 'E',
            replace : true,
            templateUrl: 'app/common/directives/multiStatsBox/multiStatsBox.html',
            scope : {
                panelStyle:'=',
                title: '=',
                items : '=',
                state: '='
            },
            link : function(scope, element, attrs){
            }

        };
    };

    angular.module('infraApp.directives').directive('multiStatBox',multiStatBox);

})();
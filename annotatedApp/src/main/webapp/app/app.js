/**
 * Created by satish on 12/06/15.
 */

(function(){
    'use strict';

    var app = angular.module('infraAppp', [
        'ui.router',
        'ui.bootstrap',
        'chart.js',
        'infraApp.dashboard',
        'infraApp.directives',
        'infraApp.resources'
    ]);


    app.config(function($urlRouterProvider, $stateProvider){

        $urlRouterProvider.otherwise('/');

        $stateProvider.state('home',{
            url : '/',
            controller : 'homeDashboardController',
            templateUrl : 'app/modules/dashboard/home/dashboardHome.html'

        }).state('client',{
            url : '/client',
            controller : 'clientDashboardController',
            templateUrl : 'app/modules/dashboard/client/clientDashboard.html'
        });
    });

})();

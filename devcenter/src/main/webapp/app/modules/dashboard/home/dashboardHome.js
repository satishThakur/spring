/**
 * Created by satish on 12/06/15.
 */
(function(){
    'use strict';

    var homeDashboardController = function($scope, resourcesStat){
        $scope.rdsLabels = [];
        $scope.rdsData = [];

        $scope.ec2Labels = [];
        $scope.ec2Data = [];

        $scope.elbLabels = [];
        $scope.elbData = [];

        $scope.s3Labels = [];
        $scope.s3Data = [];


        resourcesStat.get(function(data){
            $scope.allStats = data;
            for(var region in  $scope.allStats.rdsStats.regionStats){
                if($scope.allStats.rdsStats.regionStats.hasOwnProperty(region) && region !== 'totalCount'){
                    $scope.rdsLabels.push(region);
                    $scope.rdsData.push($scope.allStats.rdsStats.regionStats[region]);
                }
            };

            for(var ec2Region in  $scope.allStats.ec2Stats.regionStats){
                if($scope.allStats.ec2Stats.regionStats.hasOwnProperty(ec2Region) && ec2Region !== 'totalCount'){
                    $scope.ec2Labels.push(ec2Region);
                    $scope.ec2Data.push($scope.allStats.ec2Stats.regionStats[ec2Region]);
                }
            }

            for(var elbRegion in  $scope.allStats.elbStats.regionStats){
                if($scope.allStats.elbStats.regionStats.hasOwnProperty(elbRegion) && elbRegion !== 'totalCount'){
                    $scope.elbLabels.push(elbRegion);
                    $scope.elbData.push($scope.allStats.elbStats.regionStats[elbRegion]);
                }
            }

            for(var s3Region in  $scope.allStats.s3Stats.regionStats){
                if($scope.allStats.s3Stats.regionStats.hasOwnProperty(s3Region) && s3Region !== 'totalCount'){
                    $scope.s3Labels.push(s3Region);
                    $scope.s3Data.push($scope.allStats.s3Stats.regionStats[s3Region]);
                }
            }
        });


        $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
        $scope.series = ['RDS', 'EC2', 'ELB', 'S3'];
        $scope.data = [
            [40, 45, 50, 45, 47, 50, 50],
            [100, 200, 250, 300, 300, 350, 350],
            [20, 20, 25, 25, 30, 35, 40],
            [20, 40, 50, 70, 90, 93, 80]
        ];

    };

    angular.module('infraApp.dashboard', []).controller('homeDashboardController',homeDashboardController);


})();
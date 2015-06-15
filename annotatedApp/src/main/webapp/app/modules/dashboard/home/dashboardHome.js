/**
 * Created by satish on 12/06/15.
 */
(function(){
    'use strict';

    var homeDashboardController = function($scope){
        $scope.allStats = {
            rds : {
                'us-east-1' : 9,
                'us-west-2' : 40,
                'us-west-1' : 4,
                count : 53
            },
            ec2 : {
                'us-east-1' : 70,
                'us-west-2' : 230,
                'us-west-1' : 45,
                count : 345
            },
            elb : {
                'us-east-1' : 10,
                'us-west-2' : 25,
                'us-west-1' : 5,
                count : 40
            },
            s3 : {
                'us-east-1' : 19,
                'us-west-2' : 90,
                'us-west-1' : 13,
                count : 122
            }
        };

        $scope.rdsLabels = [];
        $scope.rdsData = [];

        for(var region in  $scope.allStats.rds){
            if($scope.allStats.rds.hasOwnProperty(region) && region !== 'count'){
                $scope.rdsLabels.push(region);
                $scope.rdsData.push($scope.allStats.rds[region]);
            }
        };

        $scope.ec2Labels = [];
        $scope.ec2Data = [];

        for(var ec2Region in  $scope.allStats.ec2){
            if($scope.allStats.ec2.hasOwnProperty(ec2Region) && ec2Region !== 'count'){
                $scope.ec2Labels.push(ec2Region);
                $scope.ec2Data.push($scope.allStats.ec2[ec2Region]);
            }
        }

        $scope.elbLabels = [];
        $scope.elbData = [];

        for(var elbRegion in  $scope.allStats.elb){
            if($scope.allStats.elb.hasOwnProperty(elbRegion) && elbRegion !== 'count'){
                $scope.elbLabels.push(elbRegion);
                $scope.elbData.push($scope.allStats.elb[elbRegion]);
            }
        }

        $scope.s3Labels = [];
        $scope.s3Data = [];

        for(var s3Region in  $scope.allStats.s3){
            if($scope.allStats.s3.hasOwnProperty(s3Region) && s3Region !== 'count'){
                $scope.s3Labels.push(s3Region);
                $scope.s3Data.push($scope.allStats.s3[s3Region]);
            }
        }



        $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
        $scope.series = ['RDS', 'EC2', 'ELB', 'S3'];
        $scope.data = [
            [40, 45, 50, 45, 47, 50, 50],
            [100, 200, 250, 300, 300, 350, 350],
            [20, 20, 25, 25, 30, 35, 40],
            [20, 40, 50, 70, 90, 93, 80]
        ];
        $scope.onClick = function (points, evt) {
            console.log(points, evt);
        };

    };

    angular.module('infraApp.dashboard', []).controller('homeDashboardController',homeDashboardController);


})();
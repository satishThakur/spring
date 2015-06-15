/**
 * Created by satish on 14/06/15.
 */
(function(){

    var clientDashboardController = function($scope){

        var styles = [['panel','panel-primary'],['panel','panel-green'],['panel','panel-yellow'],['panel','panel-red']];

        var instanceStyles = [['fa','fa-database','fa-2x'],['fa','fa-tasks','fa-2x'],
            ['fa','fa-arrows','fa-2x'],['fa','fa-bitbucket','fa-2x']];

        var instanceStylesMapping = {
            rds : instanceStyles[0],
            ec2 : instanceStyles[1],
            elb : instanceStyles[2],
            s3 : instanceStyles[3]
        };

        var clientStyles = {
            abc : styles[1],
            xyz : styles[2],
            fff : styles[3]
        };

        function getRandomInt(min, max) {
            return Math.floor(Math.random() * (max - min)) + min;
        }

        var getClientStyle = function(client){
            var style = clientStyles[client];

            if(style){
                return style;
            }else{
                var randomIndex = getRandomInt(0,styles.length);
                if(randomIndex >= styles.length){
                    console.log('Random generator is wrong check', randomIndex);
                    randomIndex = styles.length -1;
                }
                return styles[randomIndex];
            }
        };

        var getResourceStyle = function(resource){
            var style = instanceStylesMapping[resource];
            if(style){
                return style;
            }else{
                //TODO should define default style somewhere!!
                return instanceStyles[2];
            }

        };

        $scope.clientsRawData = {

            abc : {
                rds : 10,
                ec2 : 22,
                elb : 4,
                s3 : 20
            },
            xyz : {
                rds : 20,
                ec2 : 52,
                elb : 6,
                s3 : 10
            },
            fff : {
                rds : 3,
                ec2 : 12,
                elb : 1,
                s3 : 5
            }

        };

        $scope.clientsData = [];
        $scope.clients = [];
        for(var client in $scope.clientsRawData){
            if($scope.clientsRawData.hasOwnProperty(client)) {
                var clientData = $scope.clientsRawData[client];
                $scope.clients.push(client);
                var transformedData = {};
                transformedData.title = 'Client ' + client;
                transformedData.style = getClientStyle(client);
                transformedData.state = 'home';
                transformedData.resources = [];
                for(var resource in clientData){
                    if(clientData.hasOwnProperty(resource)){
                        var resourceData = {};
                        resourceData.style = getResourceStyle(resource);
                        resourceData.stat = clientData[resource];
                        resourceData.name = resource;
                        transformedData.resources.push(resourceData);
                    }
                }
                $scope.clientsData.push(transformedData);
            }
        }

        $scope.rdsDistribution = [];
        $scope.ec2Distribution = [];
        $scope.elbDistribution = [];
        $scope.s3Distribution = [];
        $scope.clients.forEach(function(client){
            $scope.rdsDistribution.push($scope.clientsRawData[client]['rds']);
            $scope.ec2Distribution.push($scope.clientsRawData[client]['ec2']);
            $scope.elbDistribution.push($scope.clientsRawData[client]['elb']);
            $scope.s3Distribution.push($scope.clientsRawData[client]['s3']);
        });




        console.log('clientsData', $scope.clientsData);

    };

    angular.module('infraApp.dashboard').controller('clientDashboardController',clientDashboardController);


})();



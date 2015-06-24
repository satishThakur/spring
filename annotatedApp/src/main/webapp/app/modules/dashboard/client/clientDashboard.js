/**
 * Created by satish on 14/06/15.
 */
(function(){

    var clientDashboardController = function($scope,clientStat){

        var styles = [['panel','panel-primary'],['panel','panel-green'],['panel','panel-yellow'],['panel','panel-red']];

        var instanceStyles = [['fa','fa-database','fa-2x'],['fa','fa-tasks','fa-2x'],
            ['fa','fa-arrows','fa-2x'],['fa','fa-bitbucket','fa-2x']];

        var instanceStylesMapping = {
            rdsInstancesCount : instanceStyles[0],
            ec2InstancesCount : instanceStyles[1],
            elbInstancesCount : instanceStyles[2],
            s3InstancesCount : instanceStyles[3]
        };

        var clientStyles = {
            abc : styles[1],
            xyz : styles[2],
            fff : styles[3]
        };

        var compFriendlyNames = {
            rdsInstancesCount : 'RDS',
            ec2InstancesCount : 'EC2',
            elbInstancesCount : 'ELB',
            s3InstancesCount : 'S3'

        };


        var getCompFriendlyName = function(comp){
            var friendlyName = compFriendlyNames[comp];
            if(friendlyName){
                return friendlyName;
            }else{
                return comp;
            }

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
                var randomIndex = getRandomInt(0,instanceStylesMapping.length);
                return instanceStylesMapping[randomIndex];
            }

        };

        $scope.clientsData = [];
        $scope.clients = [];
        $scope.clientsRawData = {};

        $scope.rdsDistribution = [];
        $scope.ec2Distribution = [];
        $scope.elbDistribution = [];
        $scope.s3Distribution = [];


        clientStat.get(function(data){
            $scope.clientsRawData = data.statsMapping;
            console.log('clientsData', $scope.clientsRawData);
            var clientsData = [];
            for(var client in $scope.clientsRawData){
                if($scope.clientsRawData.hasOwnProperty(client)) {
                    var clientData = $scope.clientsRawData[client];
                    $scope.clients.push(client);
                    var transformedData = {};
                    transformedData.title = 'Client ' + client;
                    transformedData.style = getClientStyle(client);
                    transformedData.state = 'home';
                    transformedData.totalResources = 0;
                    transformedData.resources = [];
                    for(var resource in clientData){
                        if(clientData.hasOwnProperty(resource)){
                            var resourceData = {};
                            resourceData.style = getResourceStyle(resource);
                            resourceData.stat = clientData[resource];
                            transformedData.totalResources = transformedData.totalResources + resourceData.stat;
                            resourceData.name = getCompFriendlyName(resource);
                            transformedData.resources.push(resourceData);
                        }
                    }
                    clientsData.push(transformedData);
                }
            };

            clientsData.sort(function(clienta, clientb){
                return clientb.totalResources - clienta.totalResources;
            });
            $scope.clientsData = clientsData;
            console.log('clientsData', $scope.clientsData);

            $scope.clients.forEach(function(client){
                $scope.rdsDistribution.push($scope.clientsRawData[client]['rdsInstancesCount']);
                $scope.ec2Distribution.push($scope.clientsRawData[client]['ec2InstancesCount']);
                $scope.elbDistribution.push($scope.clientsRawData[client]['elbInstancesCount']);
                $scope.s3Distribution.push($scope.clientsRawData[client]['s3InstancesCount']);
            });
        });





    };

    angular.module('infraApp.dashboard').controller('clientDashboardController',clientDashboardController);


})();



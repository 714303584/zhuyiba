angular.module('index_app', [])
  .controller('IndexController', function($scope, $http) {
	  
    this.longUrl;
    this.shortUrl;
    
    this.long2short = function(){
    	$http({
    	    method: 'POST',
    	    url: '/stol/api',
    	params: { 
    		longUrl: this.longUrl
    		}  
    	}).then(function successCallback(response) {
	    		if(response.data.code === '0'){
	    			$scope.index.shortUrl = response.data.data.shortUrl;
	    		}else{
	    			$scope.index.shortUrl = response.data.msg;
	    		}
    	    },
    	    function errorCallback(response) {
    	    	
    	    });
    };
  });
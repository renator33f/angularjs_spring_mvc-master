//configurações do controller de sapatos
app.controller('sapatoController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando o sapato
		// entra pra editar
		$http.get("sapato/buscarsapato/" + $routeParams.id).success(function(response) {
			$scope.sapato = response;
			
			document.getElementById("imagemSapato").src = $scope.sapato.foto;
			
				$http.get("fornecedor/listartodos").success(function(response) {
					$scope.fornecedoresList = response;
					setTimeout(function () {
						$("#selectFornecedor").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.sapato.fornecedor.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});
			
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo sapato
		$scope.sapato = {};
	}
	
	
	$scope.editarSapato = function(id) {
		$location.path('sapatoedit/' + id);
	};
	
	
	// Responsável por salvar o sapato ou editar os dados
	$scope.salvarSapato = function() {
				$scope.sapato.foto = document.getElementById("imagemSapato").getAttribute("src");
				
				$http.post("sapato/salvar", $scope.sapato).success(function(response) {
					$scope.sapato = {};
					document.getElementById("imagemSapato").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
          
	// listar todos os sapatos
	$scope.listarSapatos = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("sapato/listar/" + numeroPagina).success(function(response) {
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("sapato/totalPagina").success(function(response) {
					$scope.totalPagina = response;
				}).error(function(response) {
					erro("Erro/r: " + response);
				});
			//---------Fim total página----------
			
		}).error(function(response) {
			erro("Error: " + response);
		});
		
	};
	
	$scope.proximo = function () {
		if (new Number($scope.numeroPagina) < new Number($scope.totalPagina)) {
		 $scope.listarSapatos(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarSapatos(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover sapato passado como parametro
	$scope.removerSapato = function(codSapato) {
		$http.delete("sapato/deletar/" + codSapato).success(function(response) {
			$scope.listarSapatos($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
	
	$scope.listarFornecedores = function() {
		$http.get("fornecedor/listartodos").success(function(response) {
			$scope.fornecedoresList = response;
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
});
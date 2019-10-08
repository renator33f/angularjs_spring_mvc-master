//configurações do controller de produtos
app.controller('produtoController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando o produto
		// entra pra editar
		$http.get("produto/buscarproduto/" + $routeParams.id).success(function(response) {
			$scope.produto = response;
			
			document.getElementById("imagemProduto").src = $scope.produto.foto;
			
				$http.get("fornecedor/listartodos").success(function(response) {
					$scope.fornecedoresList = response;
					setTimeout(function () {
						$("#selectFornecedor").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.produto.fornecedor.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});
				
			
				$http.get("marca/listartodos").success(function(response) {
					$scope.marcasList = response;
					setTimeout(function () {
						$("#selectMarca").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.produto.marca.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});			
				
				
				$http.get("categoria/listartodos").success(function(response) {
					$scope.categoriasList = response;
					setTimeout(function () {
						$("#selectCategoria").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.produto.categoria.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});			
				
				
				$http.get("tamamho/listartodos").success(function(response) {
					$scope.tamanhosList = response;
					setTimeout(function () {
						$("#selectTamanho").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.produto.tamanho.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});		
				
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo produto
		$scope.produto = {};
	}
	
	
	$scope.editarProduto = function(id) {
		$location.path('produtoedit/' + id);
	};
	
	
	// Responsável por salvar o produto ou editar os dados
	$scope.salvarProduto = function() {
				$scope.produto.foto = document.getElementById("imagemProduto").getAttribute("src");
				
				$http.post("produto/salvar", $scope.produto).success(function(response) {
					$scope.produto = {};
					document.getElementById("imagemProduto").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
          
	// listar todos os produto
	$scope.listarProdutos = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("produto/listar/" + numeroPagina).success(function(response) {
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("produto/totalPagina").success(function(response) {
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
		 $scope.listarProdutos(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarProdutos(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover produto passado como parametro
	$scope.removerProduto = function(codProduto) {
		$http.delete("produto/deletar/" + codProduto).success(function(response) {
			$scope.listarProdutos($scope.numeroPagina);
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
	
	
	$scope.listarMarcas = function() {
		$http.get("marca/listartodos").success(function(response) {
			$scope.marcasList = response;
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
	$scope.listarCategorias = function() {
		$http.get("categoria/listartodos").success(function(response) {
			$scope.categoriasList = response;
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
	$scope.listarTamanhos = function() {
		$http.get("tamanho/listartodos").success(function(response) {
			$scope.tamanhosList = response;
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
});
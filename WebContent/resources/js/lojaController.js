// configrações da loja
app.controller("lojaController", function($scope, $http, $location, $routeParams) {
	
	
	$scope.listarPedidos = function () {
		$http.get("pedido/listar").success(function(response) {
			$scope.pedidosData = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
	$scope.buscarPedidoCodigo = function (codPedido) {
		$http.get("itempedido/buscarcodigo/" + $scope.filtroPedido).success(function(response) {
			$scope.pedidosData = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};		
	
	$scope.buscarNomePedido = function () {
		$http.get("pedido/buscarnomepedido/" + $scope.filtroPedido).success(function(response) {
			$scope.pedidosPesquisa = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
	$scope.removerPedido = function (codPedido) {
		$http.delete("pedido/deletar/"+codPedido).success(function(response) {
			$scope.pedidosData = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
	
	if ($routeParams.codigoPedido != null){
		$scope.codigoPedido = $routeParams.codigoPedido;
	}
	
	$scope.finalizarPedido = function () {

		$scope.pedidoObjeto.cliente  = $scope.clientesPesquisa.cliente;
		$scope.pedidoObjeto.vendedor = $scope.vendedoresPesquisa.vendedor;
		
		$http.post("pedido/finalizar", {"pedido" : $scope.pedidoObjeto,
			"itens" : $scope.itensCarrinho}).success(function(response) {
			
			$scope.pedidoObjeto = {};
			$scope.itensCarrinho = {};
			
			$location.path("loja/pedidoconfirmado/"+response);
			
			sucesso("Pedido Realizado!");
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
	
	$scope.buscarClienteNome = function () {
		$http.get("cliente/buscarnome/" + $scope.filtroCliente).success(function(response) {
			$scope.clientesPesquisa = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
	$scope.buscarVendedorNome = function () {
		$http.get("vendedor/buscarnome/" + $scope.filtroVendedor).success(function(response) {
			$scope.vendedoresPesquisa = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
	
	$scope.adicionarClienteCarrinho = function (cliente) {
		$scope.clientesPesquisa.cliente = cliente;
	//	$scope.pedidoObjeto.cliente = cliente;
	//	$scope.clienteAdicionado = cliente;
	//	$scope.clientesPesquisa = {};
	//	$scope.filtroCliente = "";
	};
	
	$scope.adicionarVendedorCarrinho = function (vendedor) {
		$scope.vendedoresPesquisa.vendedor = vendedor;
	//	$scope.pedidoObjeto.vendedor = vendedor;
	//	$scope.vendedorAdicionado = vendedor;
	//	$scope.vendedoresPesquisa = {};
	//	$scope.filtroVendedor = "";
	};
	
	
	if ($routeParams.itens != null && $routeParams.itens.length > 0){
		
		$http.get("itempedido/processar/"+ $routeParams.itens).success(function(response) {
			
			$scope.itensCarrinho = response;
			$scope.pedidoObjeto = response[0].pedido;
			
		}).error(function(response) {
			erro("Error: " + response);
		});
		
	}else {
		$scope.carrinhoProduto = new Array();
	}
	
	$scope.addProduto = function (produtoid) {
		$scope.carrinhoProduto.push(produtoid);
		
	};
	
	$scope.recalculo = function (quantidade, produto) {
		var valorTotal = new Number();
		for (var i = 0; i < $scope.itensCarrinho.length; i++){
				var valorProduto = $scope.itensCarrinho[i].produto.valor.replace("R","").replace("$", "").replace(".","").replace(",", ".");
				if ($scope.itensCarrinho[i].produto.id == produto){
					valorTotal += parseFloat(valorProduto * quantidade);
				}else {
					valorTotal += parseFloat(valorProduto * $scope.itensCarrinho[i].quantidade);
				}
				
		}
		 $scope.pedidoObjeto.valorTotal = 'R$' + valorTotal.toString();
	};
	
	
	$scope.removerProdutoCarrinho = function (produtoid) {
		
		$scope.intensTemp = new Array();
		var valorTotal = new Number();
		for (var i = 0; i < $scope.itensCarrinho.length; i++){
			if ($scope.itensCarrinho[i].produto.id === produtoid){
			}else {
				// itens validos
				$scope.intensTemp.push($scope.itensCarrinho[i]);
				
				var valorProduto = $scope.itensCarrinho[i].produto.valor.replace("R","").replace("$", "").replace(".","").replace(",", ".");
				valorTotal += parseFloat(valorTotal) + parseFloat(valorProduto * $scope.itensCarrinho[i].quantidade);
				
			};
		}
		 $scope.pedidoObjeto.valorTotal = 'R$' + valorTotal.toString();
		 $scope.itensCarrinho = $scope.intensTemp;
	};
	
	
	$scope.fecharPedido = function() {
		$location.path('loja/intensLoja/' + $scope.carrinhoProduto);
	};
	
	// listar todos os produtos
	$scope.listarProdutos = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("produto/listar/" + numeroPagina).success(function(response) {
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("produto/totalPagina").success(function(response) {
					$scope.totalPagina = response;
				}).error(function(response) {
					erro("Error: " + response);
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
	
});
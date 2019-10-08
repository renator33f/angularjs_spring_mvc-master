var permissao = false;
// configuração do módulo
var app = angular.module('loja', [ 'ngRoute', 'ngResource', 'ngAnimate' ]);


// configurando rotas
app.config(function($routeProvider) {

			$routeProvider.when("/clientelist", {
				controller : "clienteController",
				templateUrl : "cliente/list.html"
			})// listar
			
			.when("/clienteedit/:id", {
				controller : "clienteController",
				templateUrl : "cliente/cadastro.html"
			})// editar
			
			.when("/cliente/cadastro", {
				controller : "clienteController",
				templateUrl : "cliente/cadastro.html"
			})// novo
			
			//-------------Fornecedor--------------
			$routeProvider.when("/fornecedorlist", {
				controller : "fornecedorController",
				templateUrl : "fornecedor/list.html"
			})// listar
			
			.when("/fornecedoredit/:id", {
				controller : "fornecedorController",
				templateUrl : "fornecedor/cadastro.html"
			})// editar
			
			.when("/fornecedor/cadastro", {
				controller : "fornecedorController",
				templateUrl : "fornecedor/cadastro.html"
			})// novo
				
			//--------------Produto---------------------
				$routeProvider.when("/produtolist", {
				controller : "produtoController",
				templateUrl : "produto/list.html"
			})// listar
			
			.when("/produtoedit/:id", {
				controller : "produtoController",
				templateUrl : "produto/cadastro.html"
			})// editar
			
			.when("/produto/cadastro", {
				controller : "produtoController",
				templateUrl : "produto/cadastro.html"
			})// novo
			
			//--------------Tamanho---------------------
			$routeProvider.when("/tamanholist", {
				controller : "tamanhoController",
				templateUrl : "tamanho/list.html"
			})// listar
			
			.when("/tamanhoedit/:id", {
				controller : "tamanhoController",
				templateUrl : "tamanho/cadastro.html"
			})// editar
			
			.when("/tamanho/cadastro", {
				controller : "tamanhoController",
				templateUrl : "tamanho/cadastro.html"
			})// novo
			
			//--------------Marcas---------------------
			$routeProvider.when("/marcalist", {
				controller : "marcaController",
				templateUrl : "marca/list.html"
			})// listar
			
			.when("/marcaedit/:id", {
				controller : "marcaController",
				templateUrl : "marca/cadastro.html"
			})// editar
			
			.when("/marca/cadastro", {
				controller : "marcaController",
				templateUrl : "marca/cadastro.html"
			})// novo
			
			//--------------Categorias---------------------
			$routeProvider.when("/categorialist", {
				controller : "categoriaController",
				templateUrl : "categoria/list.html"
			})// listar
			
			.when("/categoriaedit/:id", {
				controller : "categoriaController",
				templateUrl : "categoria/cadastro.html"
			})// editar
			
			.when("/categoria/cadastro", {
				controller : "categoriaController",
				templateUrl : "categoria/cadastro.html"
			})// novo
			
			//--------------Vendedor---------------------
			$routeProvider.when("/vendedorlist", {
				controller : "vendedorController",
				templateUrl : "vendedor/list.html"
			})// listar
			
			.when("/vendedoredit/:id", {
				controller : "vendedorController",
				templateUrl : "vendedor/cadastro.html"
			})// editar
			
			.when("/vendedor/cadastro", {
				controller : "vendedorController",
				templateUrl : "vendedor/cadastro.html"
			})// novo
			
			//--------------Estoque Produto---------------------
			$routeProvider.when("/estoqueprodutolist", {
				controller : "estoqueProdutoController",
				templateUrl : "estoqueproduto/list.html"
			})// listar
			
			.when("/estoqueprodutoedit/:id", {
				controller : "estoqueProdutoController",
				templateUrl : "estoqueproduto/alterar.html"
			})// editar
			
			.when("/estoqueproduto/cadastro", {
				controller : "estoqueProdutoController",
				templateUrl : "estoqueproduto/cadastro.html"
			})// novo
			
			//----------------LOJA---------------
			.when("/loja/online", {
				controller : "lojaController",
				templateUrl : "loja/online.html"
			})
			.when("/loja/intensLoja/:itens", {
				controller : "lojaController",
				templateUrl : "loja/intensLoja.html"
			})
			
			.when("/loja/pedidoconfirmado/:codigoPedido", {
				controller : "lojaController",
				templateUrl : "loja/pedidoconfirmado.html"
			})
			
			.when("/loja/pedidos", {
				controller : "lojaController",
				templateUrl : "loja/pedidos.html"
			})
			
			.when("/grafico/media_pedido", {
				controller : "lojaController",
				templateUrl : "grafico/media_pedido.html"
			})
			
			.otherwise({
				redirectTo : "/"
			});
			
			
});




// configurações fornecedorController
app.controller('fornecedorController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando o fornecedor
		// entra pra editar
		$http.get("fornecedor/buscarfornecedor/" + $routeParams.id).success(function(response) {
			$scope.fornecedor = response;
			
			document.getElementById("imagemFornecedor").src = $scope.fornecedor.foto;
			//------------------ carrega estados e cidades do fornecedor em edição
			setTimeout(function () {
				$("#selectEstados").prop('selectedIndex', (new Number($scope.fornecedor.estados.id) + 1));
				
				$http.get("cidades/listar/" + $scope.fornecedor.estados.id).success(function(response) {
					$scope.cidades = response;
					setTimeout(function () {
						$("#selectCidades").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.fornecedor.cidades.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});
			
			}, 1000);
			//----------------------
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo fornecedor
		$scope.fornecedor = {};
	}
	
	
	$scope.editarFornecedor = function(id) {
		$location.path('fornecedoredit/' + id);
	};
	
	
	// Responsável por salvar o fornecedor ou editar os dados
	$scope.salvarFornecedor = function() {
				$scope.fornecedor.foto = document.getElementById("imagemFornecedor").getAttribute("src");
				
				$http.post("fornecedor/salvar", $scope.fornecedor).success(function(response) {
					$scope.fornecedor = {};
					document.getElementById("imagemFornecedor").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
          
	// listar todos os fornecedor
	$scope.listarFornecedor = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("fornecedor/listar/" + numeroPagina).success(function(response) {
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("fornecedor/totalPagina").success(function(response) {
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
		 $scope.listarFornecedor(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarFornecedor(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover fornecedor passado como parametro
	$scope.removerFornecedor = function(codForn) {
		$http.delete("fornecedor/deletar/" + codForn).success(function(response) {
			$scope.listarFornecedor($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
	// carrega as cidades de acordo com o estado passado por parametro
	$scope.carregarCidades = function(estado) {
		if (identific_nav() != 'chrome') {// executa se for diferente do chrome
			$http.get("cidades/listar/" + estado.id).success(function(response) {
				$scope.cidades = response;
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			});
	  }
	};
	
	// carrega os estados ao iniciar a tela de cadastro 
	$scope.carregarEstados = function() {
		$scope.dataEstados = [{}];
		$http.get("estados/listar").success(function(response) {
			$scope.dataEstados = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
});



// configurações do controller de clientes
app.controller('clienteController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando o cliente
		// entra pra editar
		$http.get("cliente/buscarcliente/" + $routeParams.id).success(function(response) {
			$scope.cliente = response;
			
			document.getElementById("imagemCliente").src = $scope.cliente.foto;
			//------------------ carrega estados e cidades do cliente em edição
			setTimeout(function () {
				$("#selectEstados").prop('selectedIndex', (new Number($scope.cliente.estados.id) + 1));
				
				$http.get("cidades/listar/" + $scope.cliente.estados.id).success(function(response) {
					$scope.cidades = response;
					setTimeout(function () {
						$("#selectCidades").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.cliente.cidades.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});
			
			}, 1000);
			//----------------------
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo cliente
		$scope.cliente = {};
	}
	
	
	$scope.editarCliente = function(id) {
		$location.path('clienteedit/' + id);
	};
	
	
	// Responsável por salvar o cliente ou editar os dados
	$scope.salvarCliente = function() {
				$scope.cliente.foto = document.getElementById("imagemCliente").getAttribute("src");
				
				$http.post("cliente/salvar", $scope.cliente).success(function(response) {
					$scope.cliente = {};
					document.getElementById("imagemCliente").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
          
	// listar todos os clientes
	$scope.listarClientes = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("cliente/listar/" + numeroPagina).success(function(response) {
			
			if (response == null || response == '') {
				$scope.ocultarNavegacao = true;
			}else {
				$scope.ocultarNavegacao = false;
			}
			
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("cliente/totalPagina").success(function(response) {
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
		 $scope.listarClientes(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarClientes(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover cliente passado como parametro
	$scope.removerCliente = function(codCliente) {
		$http.delete("cliente/deletar/" + codCliente).success(function(response) {
			$scope.listarClientes($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
	// carrega as cidades de acordo com o estado passado por parametro
	$scope.carregarCidades = function(estado) {
		if (identific_nav() != 'chrome') {// executa se for diferente do chrome
			$http.get("cidades/listar/" + estado.id).success(function(response) {
				$scope.cidades = response;
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			});
	  }
	};
	
	// carrega os estados ao iniciar a tela de cadastro 
	$scope.carregarEstados = function() {
		$scope.dataEstados = [{}];
		$http.get("estados/listar").success(function(response) {
			$scope.dataEstados = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
});





//configurações do controller das Marcas de sapato
app.controller('marcaController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando
		// entra pra editar
		$http.get("marca/buscarmarca/" + $routeParams.id).success(function(response) {
			$scope.marca = response;
			
			document.getElementById("codMarca").src = $scope.marca.id;
			//------------------ carrega estados e cidades do cliente em edição
			setTimeout(function () {
							
			}, 1000);
			//----------------------
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // nova marca
		$scope.marca = {};
	}
	
	
	$scope.editarMarca = function(id) {
		$location.path('marcaedit/' + id);
	};
	
	
	// Responsável por salvar a marca ou editar os dados
	$scope.salvarMarca = function() {
				$scope.marca.marca = document.getElementById("Nome").getAttribute("src");
				
				$http.post("marca/salvar", $scope.marca).success(function(response) {
					$scope.marca = {};
					document.getElementById("Nome").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
      
	// listar todas as marcas
	$scope.listarMarcas = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("marca/listar/" + numeroPagina).success(function(response) {
			
			if (response == null || response == '') {
				$scope.ocultarNavegacao = true;
			}else {
				$scope.ocultarNavegacao = false;
			}
			
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("marca/totalPagina").success(function(response) {
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
		 $scope.listarMarcas(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarMarcas(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover marca passada como parametro
	$scope.removerMarca = function(codMarca) {
		$http.delete("marca/deletar/" + codMarca).success(function(response) {
			$scope.listarMarcas($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
});





//configurações do controller das Categorias
app.controller('categoriaController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando
		// entra pra editar
		$http.get("categoria/buscarcategoria/" + $routeParams.id).success(function(response) {
			$scope.categoria = response;
			
			document.getElementById("codCategoria").src = $scope.categoria.id;
			setTimeout(function () {
							
			}, 1000);
			//----------------------
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // nova categoria
		$scope.categoria = {};
	}
	
	
	$scope.editarCategoria = function(id) {
		$location.path('categoriaedit/' + id);
	};
	
	
	// Responsável por salvar a categoria ou editar os dados
	$scope.salvarCategoria = function() {
				$scope.categoria.categoria = document.getElementById("Nome").getAttribute("src");
				
				$http.post("categoria/salvar", $scope.categoria).success(function(response) {
					$scope.categoria = {};
					document.getElementById("Nome").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
      
	// listar todas as categorias
	$scope.listarCategorias = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("categoria/listar/" + numeroPagina).success(function(response) {
			
			if (response == null || response == '') {
				$scope.ocultarNavegacao = true;
			}else {
				$scope.ocultarNavegacao = false;
			}
			
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("categoria/totalPagina").success(function(response) {
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
		 $scope.listarCategorias(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarCategorias(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover categoria passada como parametro
	$scope.removerCategoria = function(codCategoria) {
		$http.delete("categoria/deletar/" + codCategoria).success(function(response) {
			$scope.listarCategorias($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
});





//configurações do controller de tamanhos dos pares de sapato
app.controller('tamanhoController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando
		// entra pra editar
		$http.get("tamanho/buscartamanho/" + $routeParams.id).success(function(response) {
			$scope.tamanho = response;
			
			document.getElementById("codTamanho").src = $scope.tamanho.id;
			setTimeout(function () {
							
			}, 1000);
			//----------------------
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo tamanho
		$scope.tamanho = {};
	}
	
	
	$scope.editarTamanho = function(id) {
		$location.path('tamanhoedit/' + id);
	};
	
	
	// Responsável por salvar o tamanho ou editar os dados
	$scope.salvarTamanho = function() {
				$scope.tamanho.tamanho = document.getElementById("Numero").getAttribute("src");
				
				$http.post("tamanho/salvar", $scope.tamanho).success(function(response) {
					$scope.tamanho = {};
					document.getElementById("Numero").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
      
	// listar todas os tamanhos
	$scope.listarTamanhos = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("tamanho/listar/" + numeroPagina).success(function(response) {
			
			if (response == null || response == '') {
				$scope.ocultarNavegacao = true;
			}else {
				$scope.ocultarNavegacao = false;
			}
			
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("tamanho/totalPagina").success(function(response) {
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
		 $scope.listarTamanhos(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarTamanhos(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover tamanho passado como parametro
	$scope.removerTamanho = function(codTamanho) {
		$http.delete("tamanho/deletar/" + codTamanho).success(function(response) {
			$scope.listarTamanhos($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
});






//configurações do controller de vendedores
app.controller('vendedorController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando o vendedor
		// entra pra editar
		$http.get("vendedor/buscarvendedor/" + $routeParams.id).success(function(response) {
			$scope.vendedor = response;
			
			document.getElementById("imagemVendedor").src = $scope.vendedor.foto;
			//------------------ carrega estados e cidades do vendedor em edição
			setTimeout(function () {
				$("#selectEstados").prop('selectedIndex', (new Number($scope.vendedor.estados.id) + 1));
				
				$http.get("cidades/listar/" + $scope.vendedor.estados.id).success(function(response) {
					$scope.cidades = response;
					setTimeout(function () {
						$("#selectCidades").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.vendedor.cidades.id));
					}, 1000);
					
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});
			
			}, 1000);
			//----------------------
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo vendedor
		$scope.vendedor = {};
	}
	
	
	$scope.editarVendedor = function(id) {
		$location.path('vendedoredit/' + id);
	};
	
	
	// Responsável por salvar o vendedor ou editar os dados
	$scope.salvarVendedor = function() {
				$scope.vendedor.foto = document.getElementById("imagemVendedor").getAttribute("src");
				
				$http.post("vendedor/salvar", $scope.vendedor).success(function(response) {
					$scope.vendedor = {};
					document.getElementById("imagemVendedor").src = '';
					sucesso("Gravado com sucesso!");
				}).error(function(response) {
					erro("Error: " + response);
				});
  
      };
          
          
	// listar todos os vendedor
	$scope.listarVendedores = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("vendedor/listar/" + numeroPagina).success(function(response) {
			
			if (response == null || response == '') {
				$scope.ocultarNavegacao = true;
			}else {
				$scope.ocultarNavegacao = false;
			}
			
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("vendedor/totalPagina").success(function(response) {
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
		 $scope.listarVendedores(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarVendedores(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover vendedor passado como parametro
	$scope.removerVendedor = function(codVendedor) {
		$http.delete("vendedor/deletar/" + codVendedor).success(function(response) {
			$scope.listarVendedores($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
	// carrega as cidades de acordo com o estado passado por parametro
	$scope.carregarCidades = function(estado) {
		if (identific_nav() != 'chrome') {// executa se for diferente do chrome
			$http.get("cidades/listar/" + estado.id).success(function(response) {
				$scope.cidades = response;
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			});
	  }
	};
	
	// carrega os estados ao iniciar a tela de cadastro 
	$scope.carregarEstados = function() {
		$scope.dataEstados = [{}];
		$http.get("estados/listar").success(function(response) {
			$scope.dataEstados = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
});






//configurações do controller de Estoques do Produto
app.controller('estoqueProdutoController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver Atualizando o Estoque 
		// entra pra editar
		$http.get("estoqueproduto/buscarestoqueproduto/" + $routeParams.id).success(function(response) {
			$scope.estoqueproduto = response;
			
			document.getElementById("codEstoqueProduto").src = $scope.estoqueproduto.id;
					
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo Estoque Produto
		$scope.estoqueproduto = {};
	}
	
	
	$scope.editarEstoqueProduto = function(id) {
		$location.path('estoqueprodutoedit/' + id);
	};
	
	
	// Responsável por salvar o Estoque Produto ou editar os dados
	$scope.salvarEstoqueProduto = function() {
		
		 $scope.estoqueproduto.produto  = $scope.produtosPesquisa.produto;
	        
			$scope.estoqueproduto.id = document.getElementById("quantidade").getAttribute("src");
			
			$http.post("estoqueproduto/salvar", $scope.estoqueproduto).success(function(response) {
				$scope.estoqueproduto = {};
	        	// $scope.produtosPesquisa = {};
				document.getElementById("quantidade").src = '';
				sucesso("Gravado com sucesso!");
			}).error(function(response) {
				erro("Error: " + response);
			});
  
      };
      
      
    // Responsável por alterar a quantidade do estoque do produto
  	$scope.alterarEstoqueProduto = function() {
  				$scope.estoqueproduto.estoqueproduto = document.getElementById("quantidade").getAttribute("src");
  				
  				$http.post("estoqueproduto/salvar", $scope.estoqueproduto).success(function(response) {
  					$scope.estoqueproduto = {};
  					document.getElementById("quantidade").src = '';
  					sucesso("Estoque alterado com sucesso!");
  				}).error(function(response) {
  					erro("Error: " + response);
  				});
    
    };
          
          
	// listar todos os itens do estoque
	$scope.listarEstoqueProdutos = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("estoqueproduto/listar/" + numeroPagina).success(function(response) {
			
			if (response == null || response == '') {
				$scope.ocultarNavegacao = true;
			}else {
				$scope.ocultarNavegacao = false;
			}
			
			$scope.data = response;
			
			//---------Inicio total página----------
				$http.get("estoqueproduto/totalPagina").success(function(response) {
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
		 $scope.listarEstoqueProdutos(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarEstoqueProdutos(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover Estoque do Produto passado como parametro
	$scope.removerEstoqueProduto = function(codEstoqueProduto) {
		$http.delete("estoqueproduto/deletar/" + codEstoqueProduto).success(function(response) {
			$scope.listarEstoqueProdutos($scope.numeroPagina);
			sucesso("Removido com sucesso!"); 
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};
	
	
	$scope.buscarProdutoNome = function () {
		$http.get("produto/buscarnome/" + $scope.filtroProduto).success(function(response) {
			$scope.produtosPesquisa = response;
			// $scope.estoqueObjeto = response.produto;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};
	
	
    $scope.adicionarProdutoEstoque = function (produto) {
		   		
		$scope.produtosPesquisa.produto = produto;
		// $scope.estoqueAdicionado = produto;
		// $scope.produtosPesquisa = {};
			
	};
	
});








// mostra msg de sucesso
function sucesso(msg) {
    	$.notify({
        	message: msg

        },{
            type: 'success',
            timer: 1000
        });
}

// mostra msg de erro
function erro(msg) {
	$.notify({
    	message: msg

    },{
        type: 'danger',
        timer: 1000
    });
}

// faz a identificação da posição correta da cidade do registro para mostrar em edição
function buscarKeyJson(obj, key, value)
{
    for (var i = 0; i < obj.length; i++) {
        if (obj[i][key] == value) {
            return i + 2;
        }
    }
    return null;
}

// carregar cidades quando é navegador chrome usando jQuery
function carregarCidadesChrome(estado) {
	if (identific_nav() === 'chrome') {// executa se for chrome
		$.get("cidades/listarchrome", { idEstado : estado.value}, function(data) {
			 var json = JSON.parse(data);
			 html = '<option value="">--Selecione--</option>';
			 for (var i = 0; i < json.length; i++) {
				  html += '<option value='+json[i].id+'>'+json[i].nome+'</option>';
			 }
			 $('#selectCidades').html(html);
		});
  }
}

// adiciona a imagem ao campo html img
function visualizarImg() {
	 var preview = document.querySelectorAll('img').item(1);
	  var file    = document.querySelector('input[type=file]').files[0];
	  var reader  = new FileReader();

	  reader.onloadend = function () {
	    preview.src = reader.result;// carrega em base64 a img
	  };

	  if (file) {
	    reader.readAsDataURL(file);		    
	  } else {
	    preview.src = "";
	  }
	  
}

// identificar navegador
function identific_nav(){
    var nav = navigator.userAgent.toLowerCase();
    if(nav.indexOf("msie") != -1){
       return browser = "msie";
    }else if(nav.indexOf("opera") != -1){
    	return browser = "opera";
    }else if(nav.indexOf("mozilla") != -1){
        if(nav.indexOf("firefox") != -1){
        	return  browser = "firefox";
        }else if(nav.indexOf("firefox") != -1){
        	return browser = "mozilla";
        }else if(nav.indexOf("chrome") != -1){
        	return browser = "chrome";
        }
    }else{
    	alert("Navegador desconhecido!");
    }
}
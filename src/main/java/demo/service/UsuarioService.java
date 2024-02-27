package demo.service;

import demo.model.Usuario;

public interface UsuarioService {

	Iterable<Usuario> buscarTodos();
	
	Usuario buscarPorId(Long Id);
	
	void inserir(Usuario usuario);
	
	void atualizar(Long id ,Usuario usuario);
	
	void deletar(Long id);
}

package demo.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.model.EnderecoRepository;
import demo.model.Endereço;
import demo.model.Usuario;
import demo.model.UsuarioRepository;
import demo.service.UsuarioService;
import demo.service.ViaCepService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRes;
	@Autowired
	private EnderecoRepository enderecoRes;
	@Autowired
	private ViaCepService viaCep;
	
	
	@Override
	public Iterable<Usuario> buscarTodos() {
		return 	usuarioRes.findAll();

	}

	@Override
	public Usuario buscarPorId(Long Id) {
		
		Optional<Usuario> usuario = usuarioRes.findById(Id);
		return usuario.get();
	}

	@Override
	public void inserir(Usuario usuario) {
		salvarUsuarioComCep(usuario);
		
	}

	private void salvarUsuarioComCep(Usuario usuario) {
		
		String cep = usuario.getEndereço().getCep();
		
		Endereço endereco = enderecoRes.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereço novoEndereco = viaCep.consultarCep(cep);
			enderecoRes.save(novoEndereco);
			return novoEndereco;
		});
		usuario.setEndereço(endereco);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		usuarioRes.save(usuario);
	}

	@Override
	public void atualizar(Long id, Usuario usuario) {
		
		Optional<Usuario> usuarioBd = usuarioRes.findById(id);
		if (usuarioBd.isPresent()) {
			salvarUsuarioComCep(usuario);
		}else {
			System.out.println("Usuario não cadastrado");
		}
		
	}

	@Override
	public void deletar(Long usaurio) {

		usuarioRes.deleteById(usaurio);
	}

}

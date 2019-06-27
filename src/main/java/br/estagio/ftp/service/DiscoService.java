package br.estagio.ftp.service;

import br.estagio.ftp.model.Arquivos;
import br.estagio.ftp.service.exception.ErroAoSalvarArquivoException;
import br.estagio.ftp.service.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiscoService {
	

	private String raiz = "/home/alexandre/codigos/ftp";
	private String diretorioArquivos;

	@Autowired
	private UsuarioService usuarioService;
	
	public Arquivos salvarArquivo(MultipartFile arquivo, String id)  {

		this.diretorioArquivos="/"+id;
		return this.salvar(this.diretorioArquivos, arquivo,id);
	}


	
	private Arquivos salvar(String diretorio, MultipartFile arquivo,String id)  {

		Path diretorioPath = Paths.get(this.raiz, diretorio);
		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
		
		try {

			Files.createDirectories(diretorioPath);

			arquivo.transferTo(arquivoPath.toFile());

		} catch (IOException e) {

			throw new ErroAoSalvarArquivoException("Erro ao tentar salvar o arquivo");
		}

		return this.listarArquivos(id);
	}


	public Arquivos listarArquivos(String id) {

		if(this.usuarioService.consultarId(id)==null){
			return null;
			//throw new ObjetoNaoEncontradoException("Usuario "+id+" não encontrado");
		}
		this.diretorioArquivos="/"+id;

		File file = new File(this.raiz+this.diretorioArquivos);
		File[] files = file.listFiles();
		if(file.listFiles()==null){
			return null;
		}
		Arquivos arquivos = new Arquivos();
		List<String> lista = new ArrayList<>();

		if(file.listFiles().length==0){
			return null;
		}

		for(File f:files){

			lista.add(f.getName());
		}

		arquivos.setId(id);
		arquivos.setArquivos(lista);

		return arquivos;
	}

	public boolean removerArquivo(String id, String arquivo){

		this.diretorioArquivos="/"+id;

		File file = new File(this.raiz+this.diretorioArquivos);
		File[] arquivos = file.listFiles();

		if(arquivos!=null){
			for(File f:arquivos){
				if(f.getName().equals(arquivo)){
					f.delete();
					return true;
				}
			}
		}
		return false;
	}
}

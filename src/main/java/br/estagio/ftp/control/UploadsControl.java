package br.estagio.ftp.control;

import br.estagio.ftp.model.Arquivos;
import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.service.DiscoService;
import br.estagio.ftp.service.UploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/uploads")
public class UploadsControl {

    @Autowired
    private DiscoService discoService;

    @Autowired
    private UploadService uploadService;


    @PostMapping(value = "/{id}")
    @ApiOperation(value = "Efetua o upload de um arquivo " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, o upload foi concluido"),
            @ApiResponse(code = 400, message = "O usuario não foi encontrado"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso")
    }
    )
    public ResponseEntity<Arquivos> upload(@RequestParam MultipartFile arquivo, @PathVariable(value = "id") String id) throws Throwable {
        Usuario usuario = this.uploadService.localizarId(id);
        if(usuario==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.discoService.salvarArquivo(arquivo,id),HttpStatus.OK);
    }




    @ApiOperation(value = "Efetua o upload de varios arquivos " )
    @PostMapping(value = "/multiplos/{id}")
    public void multiplosUploads(@RequestParam() MultipartFile[] arquivo, @PathVariable(value = "id") String id) throws Throwable {
        System.out.println(arquivo);

        for(MultipartFile m:arquivo){
            upload(m,id);
        }

    }




    @ApiOperation(value = "Compartilha um arquivo com outros usuarios " )
    @PostMapping(value = "/{idUsuario}/{idDestino}")
    public ResponseEntity<Usuario> compartilharArquivo(
                                   @RequestParam MultipartFile arquivo,
                                   @PathVariable(value = "idUsuario") String idUsuario,
                                   @PathVariable(value = "idDestino") String idDestino) throws Throwable {
        Usuario usuario1 = this.uploadService.localizarId(idUsuario);
        Usuario usuario2 = this.uploadService.localizarId(idDestino);
        if(usuario1==null || usuario2==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.discoService.salvarArquivo(arquivo,idDestino);

        return new ResponseEntity<>(HttpStatus.OK);
    }




    @ApiOperation(value = "Lista uploads do usuario " )
    @GetMapping(value = "/{id}")
    public ResponseEntity<Arquivos> listarArquivos(@PathVariable(value = "id") String id) throws Throwable {

        Arquivos arquivos = this.discoService.listarArquivos(id);

        if(arquivos==null){
            return  new ResponseEntity<>(arquivos,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(arquivos,HttpStatus.OK);
    }




    @ApiOperation(value = "Lista arquivos dos amigos que estão compartilhados com o usuario" )
    @GetMapping(value = "/compartilhados/{id}")
    public ResponseEntity<List<Arquivos>> listarArquivosCompartilhados(@PathVariable(value = "id") String id) throws Throwable {

        List<Arquivos> arquivos = this.uploadService.localizarArquivosCompartilhados(id);

        if(arquivos == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else  if(arquivos.isEmpty()){
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(arquivos,HttpStatus.OK);
    }




    @ApiOperation(value = "Deleta arquivo do usuario " )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity removerArqvuivo(
            @PathVariable(value = "id")String id,@ApiParam(value = "nome do arquivo") @RequestParam(value = "arquivo") String arquivo) throws Throwable {

        System.out.println("variavel "+arquivo);
        Usuario usuario = this.uploadService.localizarId(id);
        if(usuario==null){
            return new ResponseEntity<>(usuario, HttpStatus.NOT_FOUND);
        }
        if(this.discoService.removerArquivo(id,arquivo)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}

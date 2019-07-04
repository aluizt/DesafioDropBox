package br.estagio.ftp.control;

import br.estagio.ftp.service.UploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/uploads")
public class UploadsControl {



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
    public void upload(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable(value = "id") String id,
            @ApiParam(value = "Nome do arquivo ")
            @RequestParam MultipartFile arquivo)  {
        this.uploadService.upload(arquivo,id);
    }



    @ApiOperation(value = "Lista uploads do usuario " )
    @GetMapping(value = "/{id}")
    public ResponseEntity<FTPFile[]> listarArquivos(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable(value = "id") String id) {

        return new ResponseEntity<>(this.uploadService.listarUploads(id),HttpStatus.OK);

    }


    @ApiOperation(value = "Lista uploads do usuario de forma paginada " )
    @GetMapping(value = "/paginados/{id}")
    public ResponseEntity<Page<FTPFile>> listarArquivosPaginados(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable(value = "id") String id,
            @ApiParam(value = "Pagina que sera exibida")
            @RequestParam("paginas") int page,
            @ApiParam(value = "Quantidade de arquivos exibidos por pagina")
            @RequestParam("quantidade") int count) {

        return new ResponseEntity<>(this.uploadService.listarUploadsPaginados(id,page,count),HttpStatus.OK);

    }


    @ApiOperation(value = "Lista arquivos dos amigos que estão compartilhados com o usuario" )
    @GetMapping(value = "/compartilhados/{id}")
    public ResponseEntity<Map<String,FTPFile[]>> listarArquivosCompartilhados(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable(value = "id") String id)  {

        Map<String, FTPFile[]> arquivos = this.uploadService.localizarArquivosCompartilhados(id);

        return new ResponseEntity<>(arquivos,HttpStatus.OK);
    }


    @ApiOperation(value = "Lista os arquivo do usuario pelo tipo de formato " )
    @GetMapping(value = "/tipos/{id}")
    public ResponseEntity<List<String>> listarArqvuivoPeloTipo(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable(value = "id")String id,
            @ApiParam(value = "Extensão do arquivo. Exemplo: png")
            @RequestParam(value = "extensao") String estensao)  {

        this.uploadService.listaUploadsPorTipo(estensao,id);

        return new ResponseEntity<>(this.uploadService.listaUploadsPorTipo(estensao,id),HttpStatus.OK);
    }


    @ApiOperation(value = "Deleta arquivo do usuario " )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity removerArqvuivo(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable(value = "id")String id,
            @ApiParam(value = "Nome do arquivo que sera removido")
            @RequestParam(value = "arquivo") String arquivo)  {

        this.uploadService.removerArquivo(arquivo,id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Efetua download de um dos arquivos do usuario")
    @GetMapping(value = "/downloads/{id}")
    public ResponseEntity baixarArquivo(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable(value = "id")String id,
            @ApiParam(value = "Nome do arquivo ")
            @RequestParam(value = "arquivo") String arquivo){

        this.uploadService.baixarArquivo(arquivo,id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}

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
    public void upload(@RequestParam MultipartFile arquivo, @PathVariable(value = "id") String id)  {
        this.uploadService.upload(arquivo,id);
    }



    @ApiOperation(value = "Lista uploads do usuario " )
    @GetMapping(value = "/{id}")
    public ResponseEntity<FTPFile[]> listarArquivos(@PathVariable(value = "id") String id) {

        return new ResponseEntity<>(this.uploadService.listarUploads(id),HttpStatus.OK);

    }


    @ApiOperation(value = "Lista uploads do usuario de forma paginada " )
    @GetMapping(value = "/paginado/{id}")
    public ResponseEntity<Page<FTPFile>> listarArquivosPaginados(
            @PathVariable(value = "id") String id,
            @RequestParam("paginas") int page,
            @RequestParam("quantidade") int count) {

        return new ResponseEntity<>(this.uploadService.listarUploadsPaginados(id,page,count),HttpStatus.OK);

    }


    @ApiOperation(value = "Lista arquivos dos amigos que estão compartilhados com o usuario" )
    @GetMapping(value = "/compartilhados/{id}")
    public ResponseEntity<Map<String,FTPFile[]>> listarArquivosCompartilhados(@PathVariable(value = "id") String id)  {

        Map<String, FTPFile[]> arquivos = this.uploadService.localizarArquivosCompartilhados(id);

        return new ResponseEntity<>(arquivos,HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta arquivo do usuario " )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity removerArqvuivo(
            @PathVariable(value = "id")String id,
            @ApiParam(value = "nome do arquivo")
            @RequestParam(value = "arquivo") String arquivo)  {

        this.uploadService.removerArquivo(arquivo,id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Efetua download de um dos arquivos do usuario")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity baixarArquivo(
                                        @PathVariable(value = "id")String id,
                                        @ApiParam(value = "nome do arquivo")
                                        @RequestParam(value = "arquivo") String arquivo){

        this.uploadService.baixarArquivo(arquivo,id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}

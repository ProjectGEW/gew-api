package br.com.gew.api.model.output;

/*
 * DTO para retornar ao front dados
 * de upload da ATA
 *
 * fileName: nome do arquivo
 * fileDownloadUri: link para download da ATA
 * fileType: formato do arquivo
 * size: tamanho do arquivo em Bytes
 * */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtaOutputDTO {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public AtaOutputDTO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

}

package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.Reclamation;
import dsi.esprit.tn.Models.reclamationFile;
import dsi.esprit.tn.repository.reclamationFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static dsi.esprit.tn.services.FTPService.TMP_UPLOAD_FOLDER;

@Service
public class reclamationFileServiceImpl implements IreclamationFileService {
    @Autowired
    reclamationFileRepository fr;

    @Autowired
    IreclamationServiceImpl IRS;


    public reclamationFile addFile(MultipartFile file, Long id) {
        Reclamation reclamation = IRS.showReclamation(id);
        try {

            FTPService.uFileUpload(file,"reclamation",id);


            reclamationFile f = new reclamationFile();
            f.setUploadDate(new Date());
            f.setFileName(file.getOriginalFilename());
            f.setFilePath(TMP_UPLOAD_FOLDER +"reclamation"+"\\"+file.getOriginalFilename());
            System.out.println("original file size: "+file.getBytes().length);
            f.setPicByte(file.getBytes());
            f.setReclamation(reclamation);
            return fr.save(f);
        }catch (Exception e){
            System.out.println("Error Uploading file");
        }
        return null;
    }
    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }


    public void removeFile(Long f, Long id) {
        reclamationFile file = fr.findById(f).orElse(null);
        if (!(file == null) ) {
            try {
                FTPService.uFileremove(file.getFileName(),"reclamation",file.getReclamation().getId());
                fr.delete(file);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    public List<reclamationFile> findAll() {
        return fr.findAll();

    }
    public reclamationFile getImageByName(String name){
        return fr.findByFileName(name).orElseThrow(() -> new UsernameNotFoundException("Image Not Found with name: " + name));
    }

    public List<reclamationFile>GetReclamationFiles(Long id){
        List<reclamationFile> list= new ArrayList<reclamationFile>();
        fr.findAll().forEach(f->{
            if (f.getReclamation().getId()==id)
                list.add(f);
        });
        return list;
    }
}

package io.github.marilialila4.debts.rest;

import io.github.marilialila4.debts.model.entity.Photo;
import io.github.marilialila4.debts.model.entity.Saving;
import io.github.marilialila4.debts.model.repository.PhotoRepository;
import io.github.marilialila4.debts.model.repository.SavingRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoRepository repository;

    @Autowired
    public PhotoController(PhotoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Photo> getAllPhoto(){
        return  repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Photo savePhoto(@RequestBody @Valid Photo photo) {
        return repository.save(photo);
    }

    @GetMapping("{id}")
    public Photo findByIdPhoto(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada!"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable Integer id){
        repository
                .findById(id)
                .map( photo -> {
                    repository.delete(photo);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadPhoto(@PathVariable Integer id, @RequestBody @Valid Photo photoUpload) {
        repository
                .findById(id)
                .map( photo -> {
                    photoUpload.setId(photo.getId());
                    return repository.save(photoUpload);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada!"));
    }

    @PutMapping("{id}/photos")
    public byte[] addPhoto(@PathVariable Integer id, @RequestParam("photo") Part arquivo){
        Optional<Photo> photo = repository.findById(id);
        return photo.map( c -> {
            try {
                InputStream is = arquivo.getInputStream();
                byte[] bytes = new byte[(int) arquivo.getSize()];
                IOUtils.readFully(is, bytes);
                c.setPhoto(bytes);
                repository.save(c);
                is.close();
                return bytes;
            } catch (IOException e) {
                return  null;
            }
        }).orElse(null);
    }
}

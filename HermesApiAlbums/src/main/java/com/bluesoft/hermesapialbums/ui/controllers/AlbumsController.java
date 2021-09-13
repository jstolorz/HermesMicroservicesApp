package com.bluesoft.hermesapialbums.ui.controllers;

import com.bluesoft.hermesapialbums.domain.AlbumEntity;
import com.bluesoft.hermesapialbums.service.AlbumsService;
import com.bluesoft.hermesapialbums.ui.model.AlbumResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/albums")
public class AlbumsController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AlbumsService albumsService;

    public AlbumsController(final AlbumsService albumsService) {
        this.albumsService = albumsService;
    }

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public List<AlbumResponseModel> userAlbums(@PathVariable String id){
        List<AlbumResponseModel> returnValue = new ArrayList<>();

        List<AlbumEntity> albumEntities = albumsService.getAlbums(id);

        if(albumEntities == null || albumEntities.isEmpty()){
            return  returnValue;
        }

        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();

        returnValue = new ModelMapper().map(albumEntities,listType);
        logger.info("Returning " + returnValue.size() + " albums");

        return returnValue;
    }


}

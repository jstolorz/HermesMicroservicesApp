package com.bluesoft.hermesapialbums.service;

import com.bluesoft.hermesapialbums.domain.AlbumEntity;

import java.util.List;

public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}
